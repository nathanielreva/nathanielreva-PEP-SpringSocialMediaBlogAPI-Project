package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByUsername(String username);
    Optional<Account> findByUsernameAndPassword(String username, String password);
    boolean existsByAccountId(int id);
}
