package com.example.app.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
	List<Topico> findByTitulo(String nomeCurso);


	List<Topico> findByCurso_Nome(String nomeCurso);
	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

	@Query("SELECT t FROM Topico t WHERE t.curso.nome= :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);

}