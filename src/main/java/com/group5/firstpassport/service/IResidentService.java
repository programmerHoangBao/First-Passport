package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.response.ViewDetailedResidentResponse;

public interface IResidentService {
  ViewDetailedResidentResponse viewDetailedResident(String identityNumber);
}
