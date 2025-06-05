package com.task_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task_service.exception.InvalidTaskException;
import com.task_service.model.dto.TaskDTO;
import com.task_service.model.entity.Task;
import com.task_service.persistence.ITaskRepository;
import com.task_service.util.TaskUtil;


@Service
public class TaskServiceImpl implements ITaskService {
    
    @Autowired
    private ITaskRepository repository;

    @Autowired
    private IUserService userService;

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public Task getTask(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Task> getTaskByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public Task createTask(TaskDTO taskDTO) {

        validateTask(taskDTO);

        Task task = Task.builder()
            .title(taskDTO.getTitle())
            .description(taskDTO.getDescription())
            .completed(taskDTO.isCompleted())
            .userId(taskDTO.getUserId())
            .title(taskDTO.getTitle()).build();

        return repository.save(task);
    }

    @Override
    public Task updateTask(Long id, TaskDTO taskDTO) {
        
        Task task = getTask(id);
        if (task == null) {
            return null;
        }

        validateTask(taskDTO);

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());

        return repository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteTasksByUserId(Long userId) {
        
        List<Task> tasks = repository.findByUserId(userId);
        repository.deleteAll(tasks);
    }

    private void validateTask(TaskDTO taskDTO) {
        TaskUtil.validateTask(taskDTO);;

        if (!userService.userExists(taskDTO.getUserId())) {
            throw new InvalidTaskException("Usuario no existe.");
        }
    }

}