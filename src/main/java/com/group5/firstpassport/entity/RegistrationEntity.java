package com.group5.firstpassport.entity;

import com.group5.firstpassport.enums.Gender;
import com.group5.firstpassport.enums.Status;
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

  @ManyToOne
  @JoinColumn(name = "IDENTITY_NUMBER", nullable = false)
  private ResidentEntity resident;

  private String fullName;
  private String address;
  @Enumerated(EnumType.STRING)
  private Gender gender;
  @Column(name = "PHONE", length = 10, nullable = false)
  private String phone;
  private String email;
  private LocalDateTime createdAt;
  @Enumerated(EnumType.STRING)
  private Status status;

  @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
  private ApprovalEntity approvalData;
}
