package com.meritamerica.week13.security.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.week13.security.models.CDAccount;

public interface CDAccountRepository extends JpaRepository<CDAccount, Integer>{

}
