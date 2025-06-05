package com.task_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task_service.client.UserClient;

import feign.FeignException;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserClient userClient;

    @Override
    public boolean userExists(Long userId) {
         try {
            Object user = userClient.getUser(userId);
            return user != null;
        } catch (FeignException.NotFound ex) {
            return false;
        }
    }

}