package com.meritamerica.week13.security.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.week13.security.models.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByUsername(String username);
}
