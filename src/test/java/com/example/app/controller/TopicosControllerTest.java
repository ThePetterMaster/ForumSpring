package com.example.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.web.server.LocalServerPort;





import static org.junit.jupiter.api.Assertions.*;

import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;


import org.springframework.boot.test.context.SpringBootTest;



import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicosControllerTest {

	@LocalServerPort
	private int port;

	//Teste Hello Word
    @Test
    void hello() {
        HelloController controller = new HelloController(); 
        String response = controller.helloo(); 
        assertEquals("Hello, World!", response);
    }
 

	//Testando Status
	@Test
	public void deveRetornarStatus200_QuandoConsultarTopicos() {
		
		given()
			.basePath("/topicos")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	// @Test
	// public void testarCadastroTopicoComSucesso() {
	// 	TopicoForm form = new TopicoForm();
	// 	form.setTitulo("Dúvida Postman");
    //     form.setMensagem("Texto da mensagem");
	// 	assertThat(form).isNotNull();
    //     assertThat(form.getTitulo()).isNotNull();
	// }

	//Testando Status e verificando atributo
	@Test
	public void testRetornarStatus201_QuandoCadastrarTopico() {
		given()
			.body("{ \"titulo\": \"Dúvida Postman\", \"mensagem\": \"Texto da mensagem\", \"nomeCurso\": \"Spring Boot\" }")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.basePath("/topicos")
			.port(port)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("titulo",equalTo("Dúvida Postman") )
			.body("mensagem",equalTo("Texto da mensagem") )
			.body("nomeCurso",equalTo("Spring Boot") );
	}
}

