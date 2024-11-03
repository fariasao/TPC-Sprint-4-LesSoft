package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.UserPDV;
public interface UserPDVRepository extends JpaRepository<UserPDV, Integer> {
    Page<UserPDV> findByNome(String userPDV, Pageable pageable);
}