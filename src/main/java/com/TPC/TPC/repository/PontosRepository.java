package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Pontos;
public interface PontosRepository extends JpaRepository<Pontos, Integer> {
    Page<Pontos> findByDataCredito(String pontos, Pageable pageable);
}