package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Notificacoes;
public interface NotificacoesRepository extends JpaRepository<Notificacoes, Integer> {
    Page<Notificacoes> findByDataEnvio(String notificacoes, Pageable pageable);
}