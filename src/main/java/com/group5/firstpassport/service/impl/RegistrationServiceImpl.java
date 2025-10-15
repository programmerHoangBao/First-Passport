package com.group5.firstpassport.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.dto.response.ViewAllRegistrationResponse;
import com.group5.firstpassport.dto.response.ViewDetailedRegistrationResponse;
import com.group5.firstpassport.entity.RegistrationEntity;
import com.group5.firstpassport.entity.ResidentEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.enums.StatusType;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.RegistrationRepository;
import com.group5.firstpassport.repository.ResidentRepository;
import com.group5.firstpassport.service.IRegistrationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
@Slf4j
public class RegistrationServiceImpl implements IRegistrationService {
  RegistrationRepository registrationRepository;
  ResidentRepository residentRepository;
  ModelMapper modelMapper;
  
  @Override
  public RegistrationResponse registration(RegistrationRequest registrationRequest) {
      Optional<ResidentEntity> residentEntityOptional = residentRepository.findByIdentityNumber(registrationRequest.getIdentityNumber());
      if (!residentEntityOptional.isPresent()) {
        throw new BadRequestException(ErrorCode.RESIDENT_NOT_FOUND);
      }
      ResidentEntity residentEntity = residentEntityOptional.get();
      RegistrationEntity registrationInput = modelMapper.map(registrationRequest, RegistrationEntity.class);
      registrationInput.setResident(residentEntity);
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
            .identityNumber(registration.getResident().getIdentityNumber())
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
    return modelMapper.map(existsRegistration.get(), ViewDetailedRegistrationResponse.class);
  }
}
