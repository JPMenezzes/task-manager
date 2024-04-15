package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.repository.IUserRepository;
import com.gerenciador.tarefas.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepository iUserRepository;

    public User saveUser(User user) {
        return iUserRepository.save(user);
    }

    public User updateUser(User user) {
        return iUserRepository.save(user);
    }

    public void excludeUser(User user) {
        iUserRepository.deleteById(user.getId());
    }

    public List<User> findAllUsers() {
        return iUserRepository.findAll();
    }
}
