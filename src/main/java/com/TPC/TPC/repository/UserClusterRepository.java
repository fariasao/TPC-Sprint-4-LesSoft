package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.UserCluster;
public interface UserClusterRepository extends JpaRepository<UserCluster, Integer> {
    Page<UserCluster> findByUserClusterID(String userCluster, Pageable pageable);
}