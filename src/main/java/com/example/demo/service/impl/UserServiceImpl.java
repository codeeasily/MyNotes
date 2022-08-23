package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author codeeasily
 * @date 2022/08/16 20:57
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> queryAll() {
        return userMapper.selectList(null);
    }

    @Override
    public List<User> queryByMap(Map<String, Object> condition) {
        return userMapper.selectByMap(condition);
    }
}
