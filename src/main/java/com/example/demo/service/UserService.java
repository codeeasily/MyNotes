package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void add(User user);

    User queryById(Integer id);

    List<User> queryAll();

    List<User> queryByMap(Map<String, Object> condition);
}
