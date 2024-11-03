package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Credit;
public interface CreditRepository extends JpaRepository<Credit, Integer> {
    Page<Credit> findByDataCredito(String credit, Pageable pageable);
}