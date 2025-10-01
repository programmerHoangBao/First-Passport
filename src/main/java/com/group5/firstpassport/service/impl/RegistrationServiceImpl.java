package com.group5.firstpassport.service.impl;

import com.group5.firstpassport.dto.request.RegistrationRequest;
import com.group5.firstpassport.dto.response.RegistrationResponse;
import com.group5.firstpassport.entity.RegistrationEntity;
import com.group5.firstpassport.entity.ResidentEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.RegistrationRepository;
import com.group5.firstpassport.repository.ResidentRepository;
import com.group5.firstpassport.service.IRegistrationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        if (!residentRepository.existsByIdentityNumber(registrationRequest.getIdentityNumber())) {
            throw new BadRequestException(ErrorCode.RESIDENT_NOT_FOUND);
        }
        ResidentEntity residentEntity = residentRepository.findByIdentityNumber(registrationRequest.getIdentityNumber()).get();
        RegistrationEntity registrationInput = modelMapper.map(registrationRequest, RegistrationEntity.class);
        registrationInput.setResident(residentEntity);
        RegistrationEntity registrationSave = registrationRepository.save(registrationInput);
        return modelMapper.map(registrationSave, RegistrationResponse.class);
    }
}
