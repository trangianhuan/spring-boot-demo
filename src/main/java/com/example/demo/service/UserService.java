package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Iterable<User> findAll();

    List<User> search(String term);

    Optional<User> findOne(Integer id);

    void save(User user);

    void delete(Integer id);

}
