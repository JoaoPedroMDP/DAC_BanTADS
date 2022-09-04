package com.bantads.gerente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bantads.gerente.model.Gerente;

public interface GerenteRepository extends JpaRepository<Gerente, Long> {
	public Gerente findByCPF(String cpf);

	@Query("from Gerente where email = :email and cpf = :cpf")
	public Gerente findByEmailAndCpf(@Param("email") String email,
	@Param("cpf") String cpf);

}
