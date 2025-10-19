package com.group5.firstpassport.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.MessageResponse;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.dto.response.ViewAllRegistrationResponse;
import com.group5.firstpassport.dto.response.ViewDetailedRegistrationResponse;
import com.group5.firstpassport.entity.ApprovalEntity;
import com.group5.firstpassport.entity.RegistrationEntity;
import com.group5.firstpassport.entity.ResidentEntity;
import com.group5.firstpassport.entity.UserEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.enums.MessageCode;
import com.group5.firstpassport.enums.StatusType;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.ApprovalRepository;
import com.group5.firstpassport.repository.RegistrationRepository;
import com.group5.firstpassport.repository.ResidentRepository;
import com.group5.firstpassport.repository.UserRepository;
import com.group5.firstpassport.service.IRegistrationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Slf4j
public class RegistrationServiceImpl implements IRegistrationService {
  RegistrationRepository registrationRepository;
  ResidentRepository residentRepository;
  ApprovalRepository approvalRepository;
  UserRepository userRepository;
  ModelMapper modelMapper;
  
  @Override
  public RegistrationResponse registration(RegistrationRequest registrationRequest) {

      RegistrationEntity registrationInput = modelMapper.map(registrationRequest, RegistrationEntity.class);
      RegistrationEntity registrationSave = registrationRepository.save(registrationInput);
      return modelMapper.map(registrationSave, RegistrationResponse.class);
  }

  @Override
  public Page<ViewAllRegistrationResponse> findAllByStatus(String statusStr, int pageSize, int pageNumber) {
    StatusType statusEnum = StatusType.valueOf(statusStr);
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<RegistrationEntity> registrationEntities = registrationRepository.findAllByStatus(statusEnum, pageable);

    if (registrationEntities.getTotalElements() == 0) {
        throw new BadRequestException(ErrorCode.NO_DATA);
    }
    return registrationEntities.map(registration ->
        ViewAllRegistrationResponse.builder()
            .id(registration.getId())
            .identityNumber(registration.getIdentityNumber())
            .fullName(registration.getFullName())
            .address(registration.getAddress())
            .gender(registration.getGender())
            .phone(registration.getPhone())
            .email(registration.getEmail())
            .createdAt(registration.getCreatedAt())
            .status(registration.getStatus())
            .build()
    );
  }

  @Override
  public ViewDetailedRegistrationResponse findDetailed(Long id) {
    Optional<RegistrationEntity> existsRegistration = registrationRepository.findById(id);
    if (!existsRegistration.isPresent()) {
      throw new BadRequestException(ErrorCode.FORM_REGISTRATION_NOT_FOUND);
    }
    ViewDetailedRegistrationResponse response = modelMapper.map(existsRegistration.get(), ViewDetailedRegistrationResponse.class);
    response.setIdentityNumber(existsRegistration.get().getIdentityNumber());
    return response;
  }

  @Override
  public MessageResponse sendFromToXD(Long formId, Long userId) {
    Optional<RegistrationEntity> existsFrom = registrationRepository.findById(formId);
    Optional<UserEntity> existsUser = userRepository.findById(userId);
    if (!existsFrom.isPresent()) {
      throw new BadRequestException(ErrorCode.FORM_REGISTRATION_NOT_FOUND);
    }
    if (existsUser.isEmpty()) {
      throw new BadRequestException(ErrorCode.USER_NO_EXIST);
    }
    log.info("User ID = {}", existsUser.get().getId());
    log.info("User name = {}", existsUser.get().getFullName());
    ApprovalEntity approval = new ApprovalEntity();
    approval.setRegistration(existsFrom.get());
    approval.setCreatedBy(existsUser.get());
    approval.setCreatedAt(LocalDateTime.now());
    ApprovalEntity approvalSave = approvalRepository.save(approval);
    if (approvalSave.getId() == null) {
      throw new BadRequestException(ErrorCode.SEND_FROM_REGISTRATION_FALIED);
    }
    log.info("Create By = {}", approvalSave.getCreatedBy().getUsername());
    RegistrationEntity updateForm = existsFrom.get();
    updateForm.setStatus(StatusType.VERIFIED);
    registrationRepository.save(updateForm);
    return MessageResponse.builder()
                  .messageCode(MessageCode.SEND_FROM_REGISTRATION_SUCCESS)
                  .timestamp(LocalDateTime.now())
                  .build();
  }

  @Override
  public MessageResponse rejectForm(Long formId) {
    Optional<RegistrationEntity> existsFrom = registrationRepository.findById(formId);
        if (!existsFrom.isPresent()) {
      throw new BadRequestException(ErrorCode.FORM_REGISTRATION_NOT_FOUND);
    }
    RegistrationEntity updateForm = existsFrom.get();
    updateForm.setStatus(StatusType.REJECTED);
    if (registrationRepository.save(updateForm).getId() == null) {
      throw new BadRequestException(ErrorCode.REJECT_FORM_FAILED);
    }
    return MessageResponse.builder()
                  .messageCode(MessageCode.REJECT_FORM_REGISTRATION_SUCCESS)
                  .timestamp(LocalDateTime.now())
                  .build();
  }
}
