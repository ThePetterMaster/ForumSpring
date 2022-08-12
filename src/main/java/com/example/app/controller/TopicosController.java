package com.example.app.controller;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.app.controller.dto.*;
import com.example.app.controller.form.*;
import com.example.app.modelo.*;
import com.example.app.repository.*;
import com.example.app.service.TopicosService;

@CrossOrigin
@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private TopicosService topicosService;
	
	//nomeCurso é um query param
	
	@GetMapping
	@Cacheable(value="listaDeTopicos")
	public Page<TopicoDto> lista(@RequestParam(required=false) String nomeCurso,@PageableDefault(sort = "id",direction=Direction.DESC,page=0,size=10) Pageable paginacao) {
		return topicosService.lista(nomeCurso,paginacao);
		//Caso o query param não tenha sido passado
		// if (nomeCurso == null) {
		// 	Page<Topico> topicos = topicoRepository.findAll(paginacao);
		// 	return TopicoDto.converter(topicos);
		// } else {
		// 	Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso,paginacao);
		// 	return TopicoDto.converter(topicos);
		// }
	}
	
	//ResponseEntity ajuda a personalizar o header, status e etc.
	@PostMapping
	@Transactional
	@CacheEvict(value="listaDeTopicos",allEntries = true)
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		// Topico topico = form.converter(cursoRepository);
		// topicoRepository.save(topico);

		// //Responde no header o caminho do topico cadastrado com o id respectivo
		// URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		// return ResponseEntity.created(uri).body(new TopicoDto(topico));
		return topicosService.cadastrar(form, uriBuilder);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		// Optional<Topico> topico = topicoRepository.findById(id);
		// if (topico.isPresent()) {
		// 	return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		// }
		
		// return ResponseEntity.notFound().build();
		return topicosService.detalhar(id);
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value="listaDeTopicos",allEntries = true)
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		// Optional<Topico> optional = topicoRepository.findById(id);
		// if (optional.isPresent()) {
		// 	Topico topico = form.atualizar(id, topicoRepository);
		// 	return ResponseEntity.ok(new TopicoDto(topico));
		// }
		
		// return ResponseEntity.notFound().build();
		return topicosService.atualizar(id,form);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value="listaDeTopicos",allEntries = true)
	public ResponseEntity<?> remover(@PathVariable Long id) {
		return topicosService.remover(id);
	}

}


