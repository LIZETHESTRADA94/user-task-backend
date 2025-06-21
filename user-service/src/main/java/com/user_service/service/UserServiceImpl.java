package com.user_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.user_service.client.TaskClient;
import com.user_service.model.dto.StatsDTO;
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

    @Autowired
    private IAzureB2CService azureB2CService;

    @Override
    public List<User> getAllUsers() { 
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")); 
    }
    
    @Override
    public UserDTO getUser(Long id) {

        User user = repository.findById(id).orElse(null);
        return loaUserDTO(user);
    }

    @Override
    public UserDTO getUser(String email) {

        User user = repository.findByEmail(email).orElse(null);
        return loaUserDTO(user);
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
                .email(userDTO.getEmail())
                .role(userDTO.getRole()).build();

        azureB2CService.createUser(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());

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
        user.setRole(userDTO.getRole());

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

    @Override
    public StatsDTO getStats() {

        return StatsDTO.builder()
            .total(repository.count())
            .build();
    }

    private UserDTO loaUserDTO(User user) {

        if (user != null) {
            List<TaskDTO> taskDTOs = taskClient.getTasksByUserId(user.getId());

            return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .tasks(taskDTOs)
                .build();

        } else {
            return null;
        }
    }

}