package com.group5.firstpassport.service;

import com.group5.firstpassport.dto.response.ViewAllLogResponse;
import org.springframework.data.domain.Page;

public interface ILogService {
  Page<ViewAllLogResponse> viewAllLog(int pageNumber, int pageSize);
}
