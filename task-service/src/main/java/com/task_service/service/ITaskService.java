package com.task_service.service;

import java.util.List;

import com.task_service.model.dto.TaskDTO;
import com.task_service.model.entity.Task;

public interface ITaskService {

    public List<Task> getAllTasks();
    public Task getTask(Long id);
    public List<Task> getTasksByUserId(Long userId);
    public List<Task> getTaskByTitle(String title);
    public Task createTask(TaskDTO taskDTO);
    public Task updateTask(Long id, TaskDTO taskDTO);
    public void deleteTask(Long id);
    public void deleteTasksByUserId(Long userId);
}