package com.group5.firstpassport.repository;

import com.group5.firstpassport.entity.ResidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<ResidentEntity,String> {
  boolean existsByIdentityNumber(String identityNumber);
  Optional<ResidentEntity> findByIdentityNumber(String identityNumber);
}
