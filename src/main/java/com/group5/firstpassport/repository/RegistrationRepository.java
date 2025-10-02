package com.group5.firstpassport.repository;

import com.group5.firstpassport.entity.RegistrationEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity,Long> {
  Optional<RegistrationEntity> findById(Long id);
}
