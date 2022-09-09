package com.bantads.auth.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bantads.auth.auth.models.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {

  Login findByEmail(String email);

}
