package com.example.app.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.example.app.controller.dto.DetalhesDoTopicoDto;
import com.example.app.controller.dto.TopicoDto;
import com.example.app.controller.form.AtualizacaoTopicoForm;
import com.example.app.controller.form.TopicoForm;
import com.example.app.modelo.Topico;
import com.example.app.repository.CursoRepository;
import com.example.app.repository.TopicoRepository;
import java.util.Optional;

@Service
public class TopicosService {
    @Autowired
	private TopicoRepository topicoRepository;
    @Autowired
	private CursoRepository cursoRepository;
    
    public Page<TopicoDto> lista(String nomeCurso, Pageable paginacao) {


        //Caso o query param não tenha sido passado
		if (nomeCurso == null) {
			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(topicos);
		} else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso,paginacao);
			return TopicoDto.converter(topicos);
		}
	}
	public ResponseEntity<TopicoDto> cadastrar( TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);

		//Responde no header o caminho do topico cadastrado com o id respectivo
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	public ResponseEntity<TopicoDto> atualizar(Long id,AtualizacaoTopicoForm form) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();
	}
	public ResponseEntity<?> remover(Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
