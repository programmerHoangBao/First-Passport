package com.group5.firstpassport.entity;

import com.group5.firstpassport.enums.ResultType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "APPROVAL_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "FORM_ID", nullable = false)
  private RegistrationEntity registration;

  @Enumerated(EnumType.STRING)
  private ResultType result;

  private LocalDateTime approvedAt;
  private String address;

  @ManyToOne
  @JoinColumn(name = "APPROVER_USER_ID")
  private UserEntity approverBy;
}
