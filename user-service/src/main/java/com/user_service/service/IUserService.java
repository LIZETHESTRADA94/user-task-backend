package com.user_service.service;

import java.util.List;

import com.user_service.model.dto.StatsDTO;
import com.user_service.model.dto.UserDTO;
import com.user_service.model.entity.User;

public interface IUserService {

    public List<User> getAllUsers();
    public UserDTO getUser(Long id);
    public UserDTO getUser(String email);
    public List<User> search(String query);
    public User createUser(UserDTO userDTO);
    public User updateUser(Long id, UserDTO userDTO);
    public void deleteUser(Long id);
    public StatsDTO getStats();
}