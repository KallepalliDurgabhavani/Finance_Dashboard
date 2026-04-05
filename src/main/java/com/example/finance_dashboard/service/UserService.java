package com.example.finance_dashboard.service;

import com.example.finance_dashboard.model.User;
import java.util.List;

public interface UserService {

    User registerUser(String username, String email, String rawPassword, String roleName);

    User findByUsername(String username);

    List<User> findAllUsers();

    void toggleUserStatus(Long userId);

    void assignRole(Long userId, String roleName);
}