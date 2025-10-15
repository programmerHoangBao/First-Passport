package com.group5.firstpassport.entity;

import com.group5.firstpassport.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "LOGS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private UserEntity user;

  @Enumerated(EnumType.STRING)
  private RoleType role;

  private String action;
  private LocalDateTime timestamp;
  private String details;
}
