package com.meritamerica.week11.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.week11.models.CDAccount;

public interface CDAccountRepository extends JpaRepository<CDAccount, Integer>{

}
