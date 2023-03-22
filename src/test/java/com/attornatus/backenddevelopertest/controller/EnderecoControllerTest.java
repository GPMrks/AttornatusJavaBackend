//package com.attornatus.backenddevelopertest.controller;
//
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.annotation.DirtiesContext;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class EnderecoControllerTest {
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
//    @LocalServerPort
//    private int port;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        RestAssured.port = port;
//    }
//
//    @Test
//    void whenGetAllAddressesByPersonIdThenCheckIfListNotNull() {
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
//                .get("/pessoas/{id}/enderecos")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        assertEquals(200, response.statusCode());
//        assertEquals("Rua John Doe", response.jsonPath().getString("[0].logradouro"));
//        assertEquals("Rua Jane Doe", response.jsonPath().getString("[1].logradouro"));
//    }
//
//    @Test
//    void whenGetMainAddressByPersonIdThenCheckIfItReallyIsTheMainAddress() {
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
//                .get("/pessoas/{id}/endereco-principal")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        assertEquals(200, response.statusCode());
//        assertTrue(response.jsonPath().getBoolean("principal"));
//    }
//
//    @Test
//    void whenUpdatePersonByIdThenCheckIfValueUpdatedChanged() {
//
//        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");
//
//        String atualizacaoPessoa = """
//                {
//                    "logradouro" : "Rua Jimmy Doe",
//                    "cep" : "50000000",
//                    "numero" : 500,
//                    "cidade" : "Test City",
//                    "principal" : false
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
//                .post("/pessoas/{id}/enderecos")
//                .then()
//                .statusCode(201)
//                .extract().response();
//
//        Response listOfPersonAdressess = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .pathParams("id", id)
//                .when()
//                .get("/pessoas/{id}/enderecos")
//                .then()
//                .extract().response();
//
//        assertEquals(201, response.statusCode());
//        assertEquals("Rua Jimmy Doe", response.jsonPath().getString("logradouro"));
//        assertEquals("Rua Jimmy Doe", listOfPersonAdressess.jsonPath().getString("[2].logradouro"));
//    }
//
//    @Test
//    void whenInformMainAddressByPersonIdThenCheckIfMainAddressChanged() {
//
//        Response post = given().contentType(ContentType.JSON).body(pessoaMock).post("/pessoas");
//
//        String atualizacaoEnderecoPrincipal = """
//                {
//                    "principal" : true
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
//        Response idEndereco = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .pathParam("id", id)
//                .when()
//                .get("/pessoas/{id}/enderecos")
//                .then()
//                .extract().response();
//
//        String idE = idEndereco.jsonPath().getString("[1].id");
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .body(atualizacaoEnderecoPrincipal)
//                .pathParam("id", id)
//                .pathParam("idE", idE)
//                .when()
//                .put("/pessoas/{id}/endereco-principal/{idE}")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        assertEquals(200, response.statusCode());
//        assertTrue(response.jsonPath().getBoolean("principal"));
//    }
//
//    @Test
//    void whenGetAddressByIdThatDoesntExistsThenReturnStatusNotFound() {
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
//                .pathParam("id", id)
//                .pathParam("idE", "IDNULL")
//                .when()
//                .get("/pessoas/{id}/enderecos/{idE}")
//                .then()
//                .extract().response();
//
//        assertEquals(404, response.statusCode());
//    }
//}
