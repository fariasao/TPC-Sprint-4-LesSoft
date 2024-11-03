package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Loja;
public interface LojaRepository extends JpaRepository<Loja, Integer> {
    Page<Loja> findByNomeLoja(String loja, Pageable pageable);
}