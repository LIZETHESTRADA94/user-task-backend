package com.user_service.service;

public interface IAzureB2CService {
    
    public void createUser(String displayName, String email, String password);
}
