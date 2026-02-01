package com.smart.services;

import com.smart.entitits.User;
import com.smart.exceptions.ResourceNotFoundException;
import com.smart.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository repository;

    @Override
    public User saveUser(User user) {
        return this.repository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        this.repository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        final User existingUser = this.repository
                .findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User with id: " + user.getId() +  " not found!"));
        // Update existing user with new details
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setAbout(user.getAbout());
        existingUser.setContacts(user.getContacts());
        existingUser.setEnabled(user.isEnabled());
        existingUser.setImageUrl(user.getImageUrl());
        return this.repository.save(existingUser);
    }

    @Override
    public User getUser(int id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User with id: " + id + " not found!"));
    }

    @Override
    public List<User> getAllUsers() {
        return this.repository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return this.repository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + email + " not found!"));
    }

}
