package com.adam.userservice.service;

import com.adam.userservice.model.User;

import java.util.List;

public interface UserService {

    public User getUser(String userId);

    public List<User> getAllUser();

    public User saveUser(User user);
}
