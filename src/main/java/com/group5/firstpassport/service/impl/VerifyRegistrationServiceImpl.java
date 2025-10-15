package com.group5.firstpassport.service.impl;

import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.entity.RegistrationEntity;
import com.group5.firstpassport.entity.ResidentEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.enums.Status;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.RegistrationRepository;
import com.group5.firstpassport.repository.ResidentRepository;
import com.group5.firstpassport.service.IVerifyRegistrationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Objects;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VerifyRegistrationServiceImpl implements IVerifyRegistrationService {
  RegistrationRepository registrationRepository;
  ResidentRepository residentRepository;
  ModelMapper modelMapper;

  @Override
  public RegistrationResponse verifyRegistration(Long id) {

    RegistrationEntity formRegistration = registrationRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ErrorCode.FORM_REGISTRATION_NOT_FOUND));
    ResidentEntity resident = residentRepository.findById(formRegistration.getResident().getIdentityNumber())
            .orElseThrow(() -> new BadRequestException(ErrorCode.RESIDENT_NOT_FOUND));
    if (check(formRegistration, resident)){
      formRegistration.setStatus(Status.VERIFIED);
    }
    else {
      formRegistration.setStatus(Status.REJECTED);
    }
    registrationRepository.save(formRegistration);
    return modelMapper.map(formRegistration, RegistrationResponse.class);
  }

  @Override
  public Page<RegistrationResponse> getRegistrations(Pageable pageable) {
    return registrationRepository.findAll(pageable)
            .map(registration -> modelMapper.map(registration, RegistrationResponse.class));
  }

  public boolean check(RegistrationEntity formRegistration, ResidentEntity resident) {
    return Objects.equals(formRegistration.getFullName(), resident.getFullName())
            && Objects.equals(formRegistration.getGender(), resident.getGender())
            && Objects.equals(formRegistration.getAddress(), resident.getAddress())
            && Objects.equals(formRegistration.getPhone(), resident.getPhone())
            && Objects.equals(formRegistration.getEmail(), resident.getEmail());
  }
}
