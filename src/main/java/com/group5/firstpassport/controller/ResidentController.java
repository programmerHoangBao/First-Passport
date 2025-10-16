package com.group5.firstpassport.controller;

import com.group5.firstpassport.dto.response.ViewDetailedResidentResponse;
import com.group5.firstpassport.service.impl.ResidentServiceImpl;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/first-passport")
@Validated
public class ResidentController {
  ResidentServiceImpl residentService;

  @GetMapping("/view-detail-resident")
  public ResponseEntity<ViewDetailedResidentResponse> viewDetailResident(
          @RequestParam
          @Pattern(regexp = "^\\d{12}$", message = "Identity Number must contain 12 digits")
          String identityNumber
  ) {
    return ResponseEntity.ok(residentService.viewDetailedResident(identityNumber));
  }
}
