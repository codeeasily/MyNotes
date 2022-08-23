package com.example.demo.controller;

import com.example.demo.api.APIResult;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author codeeasily
 * @date 2022/07/18 09:27
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/save")
    public APIResult<Void> save(@Validated @RequestBody User user) {
         userService.add(user);
         return APIResult.success();

    }

    @GetMapping("/query/{id}")
    public APIResult<User> queryById(@PathVariable("id") Integer id) {
        User user = null;
        try {
            user = userService.queryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return APIResult.success(user);
    }

    @GetMapping("query/all")
    public APIResult<List<User>> queryAll(){
        List<User> users  = userService.queryAll();
        return APIResult.success(users);
    }

    @PostMapping("query/map")
    public APIResult<List<User>> queryByMap(@RequestBody Map<String,Object> condition){
        List<User> users = userService.queryByMap(condition);
        return APIResult.success(users);
    }
}
