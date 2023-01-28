package com.attornatus.backenddevelopertest.controller;


import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.impl.EnderecoService;
import com.attornatus.backenddevelopertest.service.impl.PessoaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PessoaControllerTest {

    @LocalServerPort
    private int port;

    String pessoaMock = """
            {
                "nome" : "John Doe",
                "dataNascimento" : "01/01/1990",
                "enderecos" : [{
                    "logradouro" : "Rua John Doe",
                    "cep" : "30000000",
                    "numero" : 300,
                    "cidade" : "Test City",
                    "principal" : true
                },
                {
                    "logradouro" : "Rua Jane Doe",
                    "cep" : "40000000",
                    "numero" : 200,
                    "cidade" : "Test City"
                }]
            }""";

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.port = port;
    }

    @Test
    void whenSavePersonThenCheckIfDataHasBeenSaved() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(pessoaMock)
                .when()
                .post("/pessoas")
                .then()
                .statusCode(201)
                .extract().response();

        ResponseBody responseBody = response.getBody();

        System.out.println(responseBody.asString());

        assertEquals(201, response.statusCode());
        assertEquals("John Doe", response.jsonPath().getString("nome"));
    }

    @Test
    void whenGetAllPeopleThenCheckIfListNotNull() {

        RestAssured.given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas")
                .then()
                .statusCode(200)
                .extract().response();

//        ResponseBody responseBody = response.getBody();
//        System.out.println(responseBody.asString());

        assertEquals(200, response.statusCode());
        assertEquals("John Doe", response.jsonPath().getString("[0].nome"));
        assertNotNull(response.jsonPath().getString(""));
    }

    @Test
    void whenGetPersonByIdThenCheckIfItIsTheRightPerson() {

        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");

        Response idPessoa = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas")
                .then()
                .extract().response();

        String id = idPessoa.jsonPath().getString("[0].id");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .pathParams("id", id)
                .get("/pessoas/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(200, response.statusCode());
        assertEquals(id, response.jsonPath().getString("id"));
    }

    @Test
    void whenUpdatePersonByIdThenCheckIfValueUpdatedChanged() {

        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");

        String atualizacaoPessoa = """
                {
                    "nome": "Jane Doe",
                    "dataNascimento": "20/12/1980",
                    "enderecos": []
                }""";

        Response idPessoa = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas")
                .then()
                .extract().response();

        String id = idPessoa.jsonPath().getString("[0].id");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(atualizacaoPessoa)
                .when()
                .pathParams("id", id)
                .put("/pessoas/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(200, response.statusCode());
        assertEquals("Jane Doe", response.jsonPath().getString("nome"));
    }

    @Test
    void whenDeletePersonByIdThenCheckIfPersonWasDeleted() {

        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");

        Response idPessoa = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pessoas")
                .then()
                .extract().response();

        String id = idPessoa.jsonPath().getString("[0].id");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .pathParams("id", id)
                .delete("/pessoas/{id}")
                .then()
                .statusCode(204)
                .extract().response();

        assertEquals(204, response.statusCode());
    }
}
