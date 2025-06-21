package com.task_service.persistence;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task_service.model.entity.Task;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    List<Task> findByUserId(Long userId, Sort sort);
    
    List<Task> findByTitleContainingIgnoreCase(String title);
}
