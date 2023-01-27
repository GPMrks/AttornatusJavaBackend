package com.attornatus.backenddevelopertest.controller;


import com.attornatus.backenddevelopertest.entities.Endereco;
import com.attornatus.backenddevelopertest.entities.Pessoa;
import com.attornatus.backenddevelopertest.repository.PessoaRepository;
import com.attornatus.backenddevelopertest.service.impl.EnderecoService;
import com.attornatus.backenddevelopertest.service.impl.PessoaService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PessoaControllerTest {


    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private EnderecoService enderecoService;

    @BeforeEach
    public void setupTest() {


    }

//    @Test
//    void whenGetAllPeopleThenCheckListNotNull() {
//
//        Response response = RestAssured.given()
//                .contentType(ContentType.JSON)
//                .when()
//                .get("/api/${api.version}/pessoas")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        assertEquals(200, response.statusCode());
//        assertEquals("John Doe", response.jsonPath().getString("nome"));
//
//    }

}
