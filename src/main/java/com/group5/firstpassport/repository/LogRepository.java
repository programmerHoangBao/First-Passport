package com.group5.firstpassport.repository;

import com.group5.firstpassport.entity.LogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
  Page<LogEntity> findAll(Pageable pageable);
}
