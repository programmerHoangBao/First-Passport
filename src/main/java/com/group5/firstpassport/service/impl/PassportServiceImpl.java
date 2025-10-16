package com.group5.firstpassport.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.group5.firstpassport.dto.response.CreatePassportResponse;
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
public class PassportServiceImpl implements IPassportService{

  PassportRepository passportRepository;
  ApprovalRepository approvalRepository;
  UserRepository userRepository;
  ModelMapper modelMapper;

  @Override
  public CreatePassportResponse createPassport(Long approvalId, Long userId) {
    Optional<ApprovalEntity> existsApproval = approvalRepository.findByIdAndResult(approvalId, ResultType.APPROVED);
    Optional<UserEntity> existsUser = userRepository.findById(userId);
    if (!existsApproval.isPresent()) {
      throw new BadRequestException(ErrorCode.UNVERIFIED_INFORMATION);
    }
    if (!existsUser.isPresent()) {
      throw new BadRequestException(ErrorCode.USER_NO_EXIST);
    }
    PassportEntity passportInput = PassportEntity.builder()
                          .resident(existsApproval.get().getRegistration().getResident())
                          .fullName(existsApproval.get().getRegistration().getFullName())
                          .address(existsApproval.get().getRegistration().getAddress())
                          .gender(existsApproval.get().getRegistration().getGender())
                          .phone(existsApproval.get().getRegistration().getPhone())
                          .email(existsApproval.get().getRegistration().getEmail())
                          .createdAt(LocalDateTime.now())
                          .approval(existsApproval.get())
                          .createdBy(existsUser.get())
                          .build();
    PassportEntity passportSave = passportRepository.save(passportInput);
    if (passportSave.getId() == null) {
      throw new BadRequestException(ErrorCode.SAVE_PASSPORT_FAILED);
    }
    return modelMapper.map(passportSave, CreatePassportResponse.class);
  }

  @Override
  public Page<ViewAllRequestStoreResponse> viewAllRequestStore(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<ApprovalEntity> approvals = approvalRepository.findAllByResult(ResultType.APPROVED, pageable);
    if (approvals.isEmpty()) {
      throw new BadRequestException(ErrorCode.NO_DATA);
    }
    return approvals.map(approval -> {
      ViewAllRequestStoreResponse response = ViewAllRequestStoreResponse.builder()
              .approvalId(approval.getId())
              .createdBy(approval.getApproverBy().getUsername())
              .createdAt(approval.getApprovedAt())
              .build();
      return response;
    });
  }
}
