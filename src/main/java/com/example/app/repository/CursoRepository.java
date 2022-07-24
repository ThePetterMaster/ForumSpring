package com.example.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.modelo.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);

}
