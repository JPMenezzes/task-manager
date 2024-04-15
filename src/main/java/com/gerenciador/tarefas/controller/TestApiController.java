package com.gerenciador.tarefas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class TestApiController {

    @GetMapping("/test-api")
    public String test() {
        return "Sucesso";
    }

    @GetMapping("/test-api-welcome")
    public String testWelcome(@RequestParam(name = "nome") String nome){
        return "Olá "+ nome + ", seja bem vindo!";
    }

}
