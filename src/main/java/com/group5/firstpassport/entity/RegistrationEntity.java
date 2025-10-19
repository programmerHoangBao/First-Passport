package com.group5.firstpassport.entity;

import com.group5.firstpassport.enums.GenderType;
import com.group5.firstpassport.enums.StatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "FORM_REGISTRATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RESIDENT_ID", referencedColumnName = "IDENTITY_NUMBER")
  private ResidentEntity resident;
  @Column(name = "IDENTITY_NUMBER",nullable = false)
  private String identityNumber;
  private String fullName;
  private String address;
  @Enumerated(EnumType.STRING)
  private GenderType gender;
  @Column(name = "PHONE", length = 10, nullable = false)
  private String phone;
  private String email;
  private LocalDateTime createdAt;
  @Enumerated(EnumType.STRING)
  private StatusType status;
}
