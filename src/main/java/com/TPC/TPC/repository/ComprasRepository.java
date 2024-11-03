package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Compras;
public interface ComprasRepository extends JpaRepository<Compras, Integer> {
    Page<Compras> findByDataCompra(String compras, Pageable pageable);
}