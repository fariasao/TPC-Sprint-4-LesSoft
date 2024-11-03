package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Campanhas;
public interface CampanhasRepository extends JpaRepository<Campanhas, Integer> {
    Page<Campanhas> findByTitulo(String campanha, Pageable pageable);
}
