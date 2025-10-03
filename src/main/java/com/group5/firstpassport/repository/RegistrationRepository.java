package com.group5.firstpassport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.group5.firstpassport.entity.RegistrationEntity;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity,Long> {
  Optional<RegistrationEntity> findById(Long id);
  Page<RegistrationEntity> findAll(Pageable pageable);
}
