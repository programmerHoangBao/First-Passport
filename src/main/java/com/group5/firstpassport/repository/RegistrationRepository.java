package com.group5.firstpassport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.group5.firstpassport.entity.RegistrationEntity;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity,Long> {
  Page<RegistrationEntity> findAll(Pageable pageable);
}
