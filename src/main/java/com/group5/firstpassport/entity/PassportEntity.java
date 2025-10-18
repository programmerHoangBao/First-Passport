package com.group5.firstpassport.entity;

import java.time.LocalDateTime;

import com.group5.firstpassport.enums.GenderType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PASSPORTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "IDENTITY_NUMBER", nullable = false)
  private ResidentEntity resident;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "APPROVAL_ID", nullable = false)
  private ApprovalEntity approval;

  private String fullName;
  private String address;

  @Enumerated(EnumType.STRING)
  private GenderType gender;
  @Column(name = "PHONE", length = 10, nullable = false)
  private String phone;
  private String email;
  private LocalDateTime createdAt;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private UserEntity createdBy;
}
