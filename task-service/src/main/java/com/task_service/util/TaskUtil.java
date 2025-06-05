package com.task_service.util;

import org.springframework.util.StringUtils;

import com.task_service.exception.InvalidTaskException;
import com.task_service.model.dto.TaskDTO;

public class TaskUtil {
    public static void  validateTask(TaskDTO taskDTO) {

        if (taskDTO == null ) {
            throw new InvalidTaskException("Datos vacíos");
        }

        if (taskDTO.getTitle() == null || !StringUtils.hasLength(taskDTO.getTitle().trim())) {
            throw new InvalidTaskException("Título vacío");
        }
        
        if (taskDTO.getUserId() == null) {
            throw new InvalidTaskException("Id de usuario vacío");
        }
    }
}
