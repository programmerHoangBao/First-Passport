package com.group5.firstpassport.controller;

import com.group5.firstpassport.dto.response.ViewAllLogResponse;
import com.group5.firstpassport.service.impl.LogServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/gs")
public class LogController {
  LogServiceImpl logService;

  @GetMapping("/view-all-log")
  public ResponseEntity<Page<ViewAllLogResponse>> viewAllLog(
          @RequestParam(defaultValue = "0") int pageNumber,
          @RequestParam(defaultValue = "10") int pageSize
  ) {
    return ResponseEntity.ok(logService.viewAllLog(pageNumber, pageSize));
  }
}
