package com.group5.firstpassport.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.group5.firstpassport.dto.response.ViewAllSendFromXDResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.group5.firstpassport.dto.response.ApprovalResponse;
import com.group5.firstpassport.dto.response.ViewAllApprovalResponse;
import com.group5.firstpassport.dto.response.ViewDetailedApprovalResponse;
import com.group5.firstpassport.entity.ApprovalEntity;
import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.enums.ResultType;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.ApprovalRepository;
import com.group5.firstpassport.repository.UserRepository;
import com.group5.firstpassport.service.IApprovalService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ApprovalServiceImpl implements IApprovalService {
  ApprovalRepository approvalRepository;
  UserRepository userRepository;
  ModelMapper modelMapper;

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
      ViewAllApprovalResponse response = modelMapper.map(approval, ViewAllApprovalResponse.class);
      response.setFormId(approval.getRegistration().getId());
      if (approval.getApproverBy() != null) {
        response.setApproverBy(approval.getApproverBy().getUsername());
      }
      return response;
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
      ViewAllSendFromXDResponse response = new ViewAllSendFromXDResponse();
      response.setCreatedBy(approval.getCreatedBy().getUsername());
      response.setCreatedAt(approval.getCreatedAt());
      response.setFormId(approval.getRegistration().getId());
      return response;
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
              .identityNumber(existsApproval.get().getRegistration().getResident().getIdentityNumber())
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
}
