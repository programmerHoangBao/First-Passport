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

        RegistrationEntity form_registration = registrationRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.FORM_REGISTRATION_NOT_FOUND));
        ResidentEntity resident = residentRepository.findById(form_registration.getResident().getIdentityNumber())
                .orElseThrow(() -> new BadRequestException(ErrorCode.RESIDENT_NOT_FOUND));
        if (check(form_registration, resident)){
            form_registration.setStatus(Status.VERIFIED);
        }
        else {
            form_registration.setStatus(Status.REJECTED);
        }
        registrationRepository.save(form_registration);
        return modelMapper.map(form_registration, RegistrationResponse.class);
    }

    @Override
    public Page<RegistrationResponse> getRegistrations(Pageable pageable) {
        return registrationRepository.findAll(pageable)
                .map(registration -> modelMapper.map(registration, RegistrationResponse.class));
    }

    public boolean check(RegistrationEntity form_registration, ResidentEntity resident) {
        return Objects.equals(form_registration.getFullName(), resident.getFullName())
                && Objects.equals(form_registration.getGender(), resident.getGender())
                && Objects.equals(form_registration.getAddress(), resident.getAddress())
                && Objects.equals(form_registration.getPhone(), resident.getPhone())
                && Objects.equals(form_registration.getEmail(), resident.getEmail());
    }
}
