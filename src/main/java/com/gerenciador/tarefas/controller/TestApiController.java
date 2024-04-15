package com.gerenciador.tarefas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiController {

    @GetMapping("/teste-api")
    public String teste() {
        return "Sucesso";
    }

    @GetMapping("/teste-api-welcome")
    public String testeWelcome(@RequestParam(name = "nome") String nome){
        return "Olá "+ nome + ", seja bem vindo!";
    }

}
