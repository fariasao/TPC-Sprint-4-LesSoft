package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.CreditCompras;

public interface CreditComprasRepository extends JpaRepository<CreditCompras, Integer> {
    Page<CreditCompras> findByCreditCompraID(String creditCompras, Pageable pageable);
}