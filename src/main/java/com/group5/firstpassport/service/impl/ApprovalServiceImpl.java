package com.group5.firstpassport.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.group5.firstpassport.dto.response.ViewAllSendFromXDResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.group5.firstpassport.dto.response.ApprovalResponse;
import com.group5.firstpassport.dto.response.MessageResponse;
import com.group5.firstpassport.dto.response.ViewAllApprovalResponse;
import com.group5.firstpassport.dto.response.ViewDetailedApprovalResponse;
import com.group5.firstpassport.entity.ApprovalEntity;
import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.enums.MessageCode;
import com.group5.firstpassport.enums.ResultType;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.ApprovalRepository;
import com.group5.firstpassport.repository.UserRepository;
import com.group5.firstpassport.service.IApprovalService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.transaction.annotation.Transactional;
import org.eclipse.angus.mail.smtp.SMTPSendFailedException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ApprovalServiceImpl implements IApprovalService {
  ApprovalRepository approvalRepository;
  UserRepository userRepository;
  JavaMailSender mailSender;
  @Value("${spring.mail.username}")
  @NonFinal
  private String fromEmail;

  @Override
  @Transactional
  public ApprovalResponse approval(Long approvalId, Long approvalBy) {
    Optional<ApprovalEntity> existsApproval = approvalRepository.findById(approvalId);
    Optional<UserEntity> existsUser = userRepository.findById(approvalBy);
    if (existsApproval.isEmpty()) {
      throw new BadRequestException(ErrorCode.APPROVAL_NO_EXISTS);
    }
    if (existsUser.isEmpty()) {
      throw new BadRequestException(ErrorCode.USER_NO_EXIST);
    }
    ApprovalEntity approvalInput = existsApproval.get();
    approvalInput.setResult(ResultType.APPROVED);
    approvalInput.setApproverBy(existsUser.get());
    approvalInput.setApprovedAt(LocalDateTime.now());
    try {
      ApprovalEntity approvalSave = approvalRepository.save(approvalInput);
      return ApprovalResponse.builder()
                        .approvalId(approvalSave.getId())
                        .formId(approvalSave.getRegistration().getId())
                        .approverBy(approvalSave.getApproverBy().getUsername())
                        .approvedAt(approvalSave.getApprovedAt())
                        .build();
    }
    catch (Exception ex){
      log.error(ex.getMessage());
      throw new BadRequestException(ErrorCode.APPROVAL_FAILED);
    }
  }

  @Override
  @Transactional
  public Page<ViewAllApprovalResponse> viewAllApprovalByResult(String resultStr, int pageNumber, int pageSize) {
    ResultType resultType = ResultType.valueOf(resultStr);
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<ApprovalEntity> approvals = approvalRepository.findAllByResult(resultType, pageable);
    if (approvals.isEmpty()) {
      throw new BadRequestException(ErrorCode.NO_DATA);
    }
    return approvals.map(approval -> {
      return ViewAllApprovalResponse.builder()
                .approvalId(approval.getId())
                .formId(approval.getRegistration().getId())
                .approvedAt(approval.getApprovedAt())
                .approverBy(approval.getApproverBy().getUsername())
                .build();
    });
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ViewAllSendFromXDResponse> viewAllApprovalByResultIsNull(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<ApprovalEntity> approvals = approvalRepository.findByResultIsNull(pageable);
    if (approvals.isEmpty()) {
      throw new BadRequestException(ErrorCode.NO_DATA);
    }
    return approvals.map(approval -> {
      return ViewAllSendFromXDResponse.builder()
                  .approvalId(approval.getId())
                  .formId(approval.getRegistration().getId())
                  .createdAt(LocalDateTime.now())
                  .createdBy(approval.getCreatedBy().getUsername())
                  .build();
    });
  }

  @Override
  @Transactional
  public ViewDetailedApprovalResponse viewDetailedApproval(Long id) {
    Optional<ApprovalEntity> existsApproval = approvalRepository.findById(id);
    if (!existsApproval.isPresent()) {
      throw new BadRequestException(ErrorCode.APPROVAL_NO_EXISTS);
    }
    return ViewDetailedApprovalResponse.builder()
              .id(existsApproval.get().getId())
              .formId(existsApproval.get().getRegistration().getId())
              .fullName(existsApproval.get().getRegistration().getFullName())
              .address(existsApproval.get().getRegistration().getAddress())
              .gender(existsApproval.get().getRegistration().getGender())
              .phone(existsApproval.get().getRegistration().getPhone())
              .email(existsApproval.get().getRegistration().getEmail())
              .createdFormAt(existsApproval.get().getRegistration().getCreatedAt())
              .xtBy(existsApproval.get().getApproverBy().getUsername())
              .result(existsApproval.get().getResult())
              .approvedAt(existsApproval.get().getApprovedAt())
              .createdBY(existsApproval.get().getCreatedBy().getUsername())
              .createdAt(existsApproval.get().getCreatedAt())
              .build();
  }

  @Override
  @Transactional
  public MessageResponse rejectApproval(Long approvalId, Long approverBy) {
    Optional<ApprovalEntity> existsApproval = approvalRepository.findById(approvalId);
    Optional<UserEntity> existsUser = userRepository.findById(approverBy);
    if (existsApproval.isEmpty()) {
      throw new BadRequestException(ErrorCode.APPROVAL_NO_EXISTS);
    }
    if (existsUser.isEmpty()) {
      throw new BadRequestException(ErrorCode.USER_NO_EXIST);
    }
    ApprovalEntity approvalInput = existsApproval.get();
    approvalInput.setResult(ResultType.REJECTED);
    approvalInput.setApproverBy(existsUser.get());
    approvalInput.setApprovedAt(LocalDateTime.now());
    try {
      approvalRepository.save(approvalInput);
      
      // Gửi email thông báo từ chối
      String email = approvalInput.getRegistration().getEmail();
      String body = "Hồ sơ đăng ký passport của bạn đã bị từ chối. Vui lòng liên hệ để biết thêm thông tin chi tiết.";
      sendEmail(email, body);
      
      return MessageResponse.builder()
                .messageCode(MessageCode.REJECT_APPROVAL_SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();
    }
    catch (Exception ex) {
      log.error(ex.getMessage());
      throw new BadRequestException(ErrorCode.REJECT_APPROVAL_FAILED);
    }
  }

  private void sendEmail(String email, String body) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(fromEmail);
      message.setTo(email);
      message.setSubject("Thông báo kết quả đăng ký passport");
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
