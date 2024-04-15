package com.gerenciador.tarefas.controller;

import com.gerenciador.tarefas.service.UserService;
import com.gerenciador.tarefas.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/save")
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @RequestMapping("/update")
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
       return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @RequestMapping("/find")
    public ResponseEntity<List<User>> findAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User user){
        userService.excludeUser(user);
    }
}
