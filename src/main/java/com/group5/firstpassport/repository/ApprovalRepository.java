package com.group5.firstpassport.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.group5.firstpassport.entity.ApprovalEntity;
import com.group5.firstpassport.enums.ResultType;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
  Page<ApprovalEntity> findAllByResult(ResultType result, Pageable pageable);
  Page<ApprovalEntity> findByResultIsNull(Pageable pageable);
  Optional<ApprovalEntity> findById(Long id);
}
