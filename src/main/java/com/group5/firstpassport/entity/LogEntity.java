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

    @Column(name = "ACTOR", length = 50)
    private String actor;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", length = 20)
    private RoleType role;

    @Column(name = "ACTION", length = 255)
    private String action;

    @Column(name = "EVENT_TIME")
    private LocalDateTime eventTime;

    @Column(name = "POLICY_NAME", length = 100)
    private String policyName;

    @Column(name = "OBJECT_NAME", length = 50)
    private String objectName;

    @Lob
    @Column(name = "SQL_TEXT")
    private String sqlText;
}