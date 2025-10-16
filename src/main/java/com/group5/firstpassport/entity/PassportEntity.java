package com.group5.firstpassport.entity;

import java.time.LocalDateTime;

import com.group5.firstpassport.enums.GenderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PASSPORTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "IDENTITY_NUMBER", nullable = false)
  private ResidentEntity resident;

  private String fullName;
  private String address;

  @Enumerated(EnumType.STRING)
  private GenderType gender;
  @Column(name = "PHONE", length = 10, nullable = false)
  private String phone;
  private String email;
  private LocalDateTime createdAt;
  @ManyToOne
  @JoinColumn(nullable = false)
  private UserEntity createdBy;
}
