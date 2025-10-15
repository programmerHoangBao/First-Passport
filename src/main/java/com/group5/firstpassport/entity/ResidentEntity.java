package com.group5.firstpassport.entity;

import com.group5.firstpassport.enums.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "RESIDENT_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentEntity {
  @Id
  @Column(name = "IDENTITY_NUMBER", length = 12)
  private String identityNumber;
  private String fullName;
  private LocalDate dateOfBirth;
  @Enumerated(EnumType.STRING)
  private GenderType gender;
  private String address;
  @Column(name = "PHONE", length = 10, nullable = false)
  private String phone;
  private String email;

  @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
  private List<RegistrationEntity> registrations;
}