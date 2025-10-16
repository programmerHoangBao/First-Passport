package com.group5.firstpassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group5.firstpassport.entity.PassportEntity;

public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
  
}