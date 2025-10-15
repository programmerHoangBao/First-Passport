package com.group5.firstpassport.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.group5.firstpassport.dto.request.ApprovalRequest;
import com.group5.firstpassport.dto.response.ApprovalResponse;
import com.group5.firstpassport.dto.response.ViewAllApprovalResponse;
import com.group5.firstpassport.entity.ApprovalEntity;
import com.group5.firstpassport.entity.RegistrationEntity;
import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.enums.ResultType;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.ApprovalRepository;
import com.group5.firstpassport.repository.RegistrationRepository;
import com.group5.firstpassport.repository.UserRepository;
import com.group5.firstpassport.service.IApprovalService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApprovalServiceImpl implements IApprovalService {
  ApprovalRepository approvalRepository;
  RegistrationRepository registrationRepository;
  UserRepository userRepository;
  ModelMapper modelMapper;

  @Override
  public ApprovalResponse approval(ApprovalRequest approvalRequest) {
    Optional<RegistrationEntity> existsRegistration = registrationRepository.findById(approvalRequest.getFormId());
    Optional<UserEntity> existsUser = userRepository.findById(approvalRequest.getApproverBy());
    if (!existsRegistration.isPresent()) {
      throw new BadRequestException(ErrorCode.FORM_REGISTRATION_NOT_FOUND);
    }
    if (!existsUser.isPresent()) {
      throw new BadRequestException(ErrorCode.USER_NO_EXIST);
    }

    ApprovalEntity approvalInput = modelMapper.map(approvalRequest, ApprovalEntity.class);
    approvalInput.setRegistration(existsRegistration.get());
    approvalInput.setApproverBy(existsUser.get());
    ApprovalEntity approvalSaved = approvalRepository.save(approvalInput);
    ApprovalResponse approvalResponse = modelMapper.map(approvalSaved, ApprovalResponse.class);
    approvalResponse.setFormId(approvalSaved.getRegistration().getId());
    approvalResponse.setApproverBy(approvalSaved.getApproverBy().getId());
    return approvalResponse;
  }

  @Override
  public Page<ViewAllApprovalResponse> viewAllApproval(String resultStr, int pageNumber, int pageSize) {
    ResultType resultType = ResultType.valueOf(resultStr);
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<ApprovalEntity> approvals = approvalRepository.findAllByResult(resultType, pageable);
    if (approvals.isEmpty()) {
      throw new BadRequestException(ErrorCode.NO_DATA);
    }
    return approvals.map(approval -> {
      ViewAllApprovalResponse response = modelMapper.map(approval, ViewAllApprovalResponse.class);
      response.setFormId(approval.getRegistration().getId());
      response.setApproverBy(approval.getApproverBy().getFullName());
      return response;
    });
  }
}
