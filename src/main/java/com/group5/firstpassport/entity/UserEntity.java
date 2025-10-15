package com.group5.firstpassport.entity;

import com.group5.firstpassport.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "USERNAME", length = 50, nullable = false, unique = true)
  private String username;

  @Column(name = "PASSWORD", length = 255, nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  private RoleType role;

  private String fullName;
}
