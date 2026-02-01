package com.smart.services;

import com.smart.entitits.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    void deleteUser(int id);
    User updateUser(User user);
    User getUser(int id);
    List<User> getAllUsers();
    User getUserByEmail(String email);
}
