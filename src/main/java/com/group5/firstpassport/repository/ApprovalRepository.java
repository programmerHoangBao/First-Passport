package com.group5.firstpassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group5.firstpassport.entity.ApprovalEntity;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {

}
