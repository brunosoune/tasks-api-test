package br.so.polone.tasks.apitest;

import java.nio.charset.CoderMalfunctionError;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}

	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured
		.given()
			.body("{\"task\":\"arrumar a mesa para o café\",\"dueDate\":\"2020-08-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
			
		.then()
			.log().all()
			.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured
		.given()
			.body("{\"task\":\"arrumar a mesa para o café\",\"dueDate\":\"2010-08-04\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
			
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}