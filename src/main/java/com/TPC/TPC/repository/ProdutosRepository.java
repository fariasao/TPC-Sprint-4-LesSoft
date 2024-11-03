package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Produtos;
public interface ProdutosRepository extends JpaRepository<Produtos, Integer> {
    Page<Produtos> findByValor(String produtos, Pageable pageable);
}