package com.user_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user_service.model.dto.TaskDTO;

@FeignClient(name = "task-service")
public interface TaskClient {

    @GetMapping("/v1/api/task/user/{userId}")
    public List<TaskDTO> getTasksByUserId(@PathVariable Long userId);

    @DeleteMapping("/v1/api/task/user/{userId}")
    public void deleteTasksByUserId(@PathVariable Long userId);
    
}