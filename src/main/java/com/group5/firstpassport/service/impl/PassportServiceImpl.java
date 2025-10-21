package com.group5.firstpassport.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Optional;

import com.group5.firstpassport.dto.response.MessageResponse;
import com.group5.firstpassport.enums.MessageCode;
import com.group5.firstpassport.repository.RegistrationRepository;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.angus.mail.smtp.SMTPSendFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group5.firstpassport.dto.response.ViewAllRequestStoreResponse;
import com.group5.firstpassport.entity.ApprovalEntity;
import com.group5.firstpassport.entity.PassportEntity;
import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.enums.ResultType;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.ApprovalRepository;
import com.group5.firstpassport.repository.PassportRepository;
import com.group5.firstpassport.repository.UserRepository;
import com.group5.firstpassport.service.IPassportService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PassportServiceImpl implements IPassportService{
  JdbcTemplate jdbcTemplate;
  JavaMailSender mailSender;
  PassportRepository passportRepository;
  ApprovalRepository approvalRepository;
  UserRepository userRepository;
  @Value("${spring.mail.username}")
  @NonFinal
  private String fromEmail;

  @Override
  @Transactional
  public MessageResponse createPassport(Long approvalId, Long userId) {
    Optional<ApprovalEntity> existsApproval = approvalRepository.findByIdAndResult(approvalId, ResultType.APPROVED);
    Optional<UserEntity> existsUser = userRepository.findById(userId);
    if (!existsApproval.isPresent()) {
      throw new BadRequestException(ErrorCode.UNVERIFIED_INFORMATION);
    }
    if (!existsUser.isPresent()) {
      throw new BadRequestException(ErrorCode.USER_NO_EXIST);
    }
    PassportEntity passportInput = PassportEntity.builder()
                          .createdAt(LocalDateTime.now())
                          .approval(existsApproval.get())
                          .createdBy(existsUser.get())
                          .build();
    PassportEntity passportSave = passportRepository.save(passportInput);
    if (passportSave.getId() == null) {
      throw new BadRequestException(ErrorCode.SAVE_PASSPORT_FAILED);
    }
    ApprovalEntity approvalEntity = existsApproval.get();
    approvalEntity.setDeleted(true);
    approvalRepository.save(approvalEntity);
    jdbcTemplate.execute((Connection conn) -> {
      try (CallableStatement cs = conn.prepareCall("{call set_passport_ctx_pkg.set_user_info(?, ?)}")) {
        cs.setString(1, "SYSTEM");
        cs.setString(2, "XD");
        cs.execute();
      }
      return null;
    });
    String email = approvalEntity.getRegistration().getEmail();
    String body = "Đăng Ký Thành Công";
    sendEmail(email, body);
    return MessageResponse.builder()
                .messageCode(MessageCode.CREATE_PASSPORT_SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();
  }

  @Override
  @Transactional
  public Page<ViewAllRequestStoreResponse> viewAllRequestStore(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<ApprovalEntity> approvals = approvalRepository
              .findByResultAndIsDeleted(ResultType.APPROVED, false, pageable);
    if (approvals.isEmpty()) {
      throw new BadRequestException(ErrorCode.NO_DATA);
    }
    return approvals.map(approval -> {
      return ViewAllRequestStoreResponse.builder()
              .approvalId(approval.getId())
              .createdBy(approval.getApproverBy().getUsername())
              .createdAt(approval.getApprovedAt())
              .build();
    });
  }

  @Override
  @Transactional
  public MessageResponse rejectPassport(Long approvalId, Long userId) {
    Optional<ApprovalEntity> existsApproval = approvalRepository
              .findByIdAndResultAndIsDeleted(approvalId, ResultType.APPROVED, false);
    Optional<UserEntity> existsUser = userRepository.findById(userId);
    if (!existsApproval.isPresent()) {
      throw new BadRequestException(ErrorCode.UNVERIFIED_INFORMATION);
    }
    if (!existsUser.isPresent()) {
      throw new BadRequestException(ErrorCode.USER_NO_EXIST);
    }
    try {
      approvalRepository.deleteById(approvalId);
      return MessageResponse.builder()
              .messageCode(MessageCode.REJECT_REQUEST_STORE_SUCCESS)
              .timestamp(LocalDateTime.now())
              .build();
    }
    catch (Exception e) {
      log.error(e.getMessage());
      throw new BadRequestException(ErrorCode.REJECT_REQUEST_STORE_FAILED);
    }
  }

  private void sendEmail(String email,String body) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(fromEmail);
      message.setTo(email);
      message.setSubject("Thông báo kết quả đăng ký passport : ");
      message.setText(body);
      mailSender.send(message);
    }
    catch (MailException ex) {
      Throwable rootCause = ex.getCause();
    if (rootCause instanceof SMTPSendFailedException) {
      log.error("SMTP failed to send email: {}", rootCause.getMessage());
    } else {
      log.error("General mail exception: {}", ex.getMessage());
    }
      throw new BadRequestException(ErrorCode.EMAIL_SENDING_FAILED);
    }
  }
}
