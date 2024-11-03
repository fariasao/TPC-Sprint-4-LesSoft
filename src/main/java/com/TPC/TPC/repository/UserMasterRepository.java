package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.UserMaster;
public interface UserMasterRepository extends JpaRepository<UserMaster, Integer> {
    Page<UserMaster> findByNome(String userMaster, Pageable pageable);
}