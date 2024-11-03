package com.TPC.TPC.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TPC.TPC.model.Users;
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Page<Users> findByNome(String user, Pageable pageable);
}