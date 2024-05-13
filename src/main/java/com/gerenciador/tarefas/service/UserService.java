package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.repository.IUserRepository;
import com.gerenciador.tarefas.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return iUserRepository.save(user);

    }

    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return iUserRepository.save(user);
    }

    public void excludeUser(User user) {
        iUserRepository.deleteById(user.getId());
    }

    public List<User> findAllUsers() {
        return iUserRepository.findAll();
    }
}
