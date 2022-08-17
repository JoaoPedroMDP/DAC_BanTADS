package com.bantads.cliente.cliente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bantads.cliente.cliente.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  // Cliente findByEmail(String email);

}
