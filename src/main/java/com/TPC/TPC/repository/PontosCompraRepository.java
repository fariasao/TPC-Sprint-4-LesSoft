package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.PontosCompra;

public interface PontosCompraRepository extends JpaRepository<PontosCompra, Integer>{
    Page<PontosCompra> findByPontosCompraID(String pontosCompra, Pageable pageable);
}