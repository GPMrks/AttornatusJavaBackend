//package com.attornatus.backenddevelopertest.controller;
//
//
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import io.restassured.response.ResponseBody;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.annotation.DirtiesContext;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class PessoaControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    String pessoaMock = """
//            {
//                "nome" : "John Doe",
//                "dataNascimento" : "01/01/1990",
//                "enderecos" : [{
//                    "logradouro" : "Rua John Doe",
//                    "cep" : "30000000",
//                    "numero" : 300,
//                    "cidade" : "Test City",
//                    "principal" : true
//                },
//                {
//                    "logradouro" : "Rua Jane Doe",
//                    "cep" : "40000000",
//                    "numero" : 200,
//                    "cidade" : "Test City"
//                }]
//            }""";
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        RestAssured.port = port;
//    }
//
//    @Test
//    void whenSavePersonThenCheckIfDataHasBeenSaved() {
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .body(pessoaMock)
//                .when()
//                .post("/pessoas")
//                .then()
//                .statusCode(201)
//                .extract().response();
//
//        ResponseBody responseBody = response.getBody();
//
//        System.out.println(responseBody.asString());
//
//        assertEquals(201, response.statusCode());
//        assertEquals("John Doe", response.jsonPath().getString("nome"));
//    }
//
//    @Test
//    void whenGetAllPeopleThenCheckIfListNotNull() {
//
//        RestAssured.given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/pessoas")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
////        ResponseBody responseBody = response.getBody();
////        System.out.println(responseBody.asString());
//
//        assertEquals(200, response.statusCode());
//        assertEquals("John Doe", response.jsonPath().getString("[0].nome"));
//        assertNotNull(response.jsonPath().getString(""));
//    }
//
//    @Test
//    void whenGetPersonByIdThenCheckIfItIsTheRightPerson() {
//
//        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");
//
//        Response idPessoa = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/pessoas")
//                .then()
//                .extract().response();
//
//        String id = idPessoa.jsonPath().getString("[0].id");
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .pathParams("id", id)
//                .when()
//                .get("/pessoas/{id}")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        assertEquals(200, response.statusCode());
//        assertEquals(id, response.jsonPath().getString("id"));
//    }
//
//    @Test
//    void whenUpdatePersonByIdThenCheckIfValueUpdatedChanged() {
//
//        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");
//
//        String atualizacaoPessoa = """
//                {
//                    "nome": "Jane Doe",
//                    "dataNascimento": "20/12/1980",
//                    "enderecos": []
//                }""";
//
//        Response idPessoa = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/pessoas")
//                .then()
//                .extract().response();
//
//        String id = idPessoa.jsonPath().getString("[0].id");
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .body(atualizacaoPessoa)
//                .pathParams("id", id)
//                .when()
//                .put("/pessoas/{id}")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        assertEquals(200, response.statusCode());
//        assertEquals("Jane Doe", response.jsonPath().getString("nome"));
//    }
//
//    @Test
//    void whenDeletePersonByIdThenCheckIfPersonWasDeleted() {
//
//        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");
//
//        Response idPessoa = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/pessoas")
//                .then()
//                .extract().response();
//
//        String id = idPessoa.jsonPath().getString("[0].id");
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .pathParams("id", id)
//                .when()
//                .delete("/pessoas/{id}")
//                .then()
//                .statusCode(204)
//                .extract().response();
//
//        assertEquals(204, response.statusCode());
//    }
//
//    @Test
//    void whenGetPersonByIdThatDoesntExistsThenReturnStatusNotFound() {
//
//        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .pathParams("id", "IDNULL")
//                .when()
//                .get("/pessoas/{id}")
//                .then()
//                .statusCode(404)
//                .extract().response();
//
//        assertEquals(404, response.statusCode());
//    }
//}
