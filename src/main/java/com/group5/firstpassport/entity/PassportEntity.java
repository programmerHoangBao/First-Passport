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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "APPROVAL_ID", nullable = false)
  private ApprovalEntity approval;
  private LocalDateTime createdAt;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CREATED_BY", nullable = false)
  private UserEntity createdBy;
}
