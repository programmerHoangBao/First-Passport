package com.group5.firstpassport.service.impl;

import com.group5.firstpassport.dto.response.ViewDetailedResidentResponse;
import com.group5.firstpassport.entity.ResidentEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.ResidentRepository;
import com.group5.firstpassport.service.IResidentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class ResidentServiceImpl implements IResidentService {
  ResidentRepository residentRepository;
  ModelMapper modelMapper;

  @Override
  public ViewDetailedResidentResponse viewDetailedResident(String identityNumber) {
    Optional<ResidentEntity> existingResident = residentRepository.findByIdentityNumber(identityNumber);
    if (existingResident.isPresent()) {
      return modelMapper.map(existingResident.get(), ViewDetailedResidentResponse.class);
    }
    throw new BadRequestException(ErrorCode.RESIDENT_NO_EXIST);
  }
}
