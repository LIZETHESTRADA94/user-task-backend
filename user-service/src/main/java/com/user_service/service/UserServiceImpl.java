package com.user_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user_service.client.TaskClient;
import com.user_service.model.dto.TaskDTO;
import com.user_service.model.dto.UserDTO;
import com.user_service.model.entity.User;
import com.user_service.persistence.IUserRepository;
import com.user_service.util.UserUtil;

@Service
public class UserServiceImpl implements IUserService {
    
    @Autowired
    private IUserRepository repository;

    @Autowired
    private TaskClient taskClient;

    @Override
    public List<User> getAllUsers() { 
        return repository.findAll(); 
    }
    
    @Override
    public UserDTO getUser(Long id) {

        User user = repository.findById(id).orElse(null);

        if (user != null) {
            List<TaskDTO> taskDTOs = taskClient.getTasksByUserId(id);

            return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .tasks(taskDTOs)
                .build();

        } else {
            return null;
        }
    }

    @Override
    public List<User> search(String query) {
        return repository.searchByAny(query);
    }

    @Override
    public User createUser(UserDTO userDTO) { 
        
        UserUtil.validateUser(userDTO);

        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail()).build();

        return repository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {

        User user = repository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }

        UserUtil.validateUser(userDTO);

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        return repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        
        User user = repository.findById(id).orElse(null);
		if (user != null) {
            repository.delete(user);
            taskClient.deleteTasksByUserId(user.getId());
		}
    }

}