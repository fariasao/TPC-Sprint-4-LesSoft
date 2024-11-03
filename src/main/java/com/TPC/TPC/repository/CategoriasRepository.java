package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Categorias;
public interface CategoriasRepository extends JpaRepository<Categorias, Integer> {
    Page<Categorias> findByNome(String categorias, Pageable pageable);
}