package com.bantads.gerente.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bantads.gerente.model.Gerente;
import com.bantads.gerente.model.GerenteDTO;
import com.bantads.gerente.repository.GerenteRepository;
@CrossOrigin
@RestController
public class GerenteREST {
	public static List<Gerente> lista = new ArrayList<>();
	@Autowired
	private GerenteRepository repo;
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/gerente")
	List<GerenteDTO> listarTodos() {
		List<Gerente> lista = repo.findAll();
		// Converte lista de Entity para lista DTO
		return lista.stream().map(e -> mapper.map(e, GerenteDTO.class)).collect(Collectors.toList());
	}

	@GetMapping("/gerente/{id}")
	public Gerente obterTodosGerentes(@PathVariable("id") int id) {
		Gerente g = lista.stream().filter(gere -> gere.getId() == id).findAny().orElse(null);
		return g;
	}

	@PostMapping("/gerente")
	GerenteDTO inserir(@RequestBody GerenteDTO gerente) {
		// salva a Entidade convertida do DTO
		repo.save(mapper.map(gerente, Gerente.class));
		// busca o usuário inserido
		Gerente ger = repo.findByCPF(gerente.getCpf());
		// retorna o DTO equivalente à entidade
		return mapper.map(ger, GerenteDTO.class);
	}

	@PutMapping("/gerente/{id}")
	public Gerente alterarGerente(@PathVariable("id") int id, @RequestBody Gerente gerente) {
		Gerente g = lista.stream().filter(gere -> gere.getId() == id).findAny().orElse(null);
		if (g != null) {
			g.setNome(gerente.getNome());
			g.setCpf(gerente.getCpf());
			g.setEmail(gerente.getEmail());
		}
		return g;
	}

	@DeleteMapping("/gerente/{id}")
	public Gerente removerGerente(@PathVariable("id") int id) {
		Gerente gerente = lista.stream().filter(gere -> gere.getId() == id).findAny().orElse(null);
		if (gerente != null)
			lista.removeIf(g -> g.getId() == id);
		return gerente;
	}

}
