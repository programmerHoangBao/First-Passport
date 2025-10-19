package com.group5.firstpassport.service.impl;

import com.group5.firstpassport.dto.response.ViewAllLogResponse;
import com.group5.firstpassport.entity.LogEntity;
import com.group5.firstpassport.enums.ErrorCode;
import com.group5.firstpassport.exception.BadRequestException;
import com.group5.firstpassport.repository.LogRepository;
import com.group5.firstpassport.service.ILogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LogServiceImpl implements ILogService {
  LogRepository logRepository;
  ModelMapper modelMapper;

  @Override
  public Page<ViewAllLogResponse> viewAllLog(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<LogEntity> logs = logRepository.findAllByOrderByEventTimeDesc(pageable);
    if (logs.getTotalElements() == 0) {
      throw new BadRequestException(ErrorCode.NO_DATA);
    }
    return logs.map(log -> {
      return modelMapper.map(log, ViewAllLogResponse.class);
    });
  }
}
