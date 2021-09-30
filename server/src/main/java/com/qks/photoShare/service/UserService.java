package com.qks.photoShare.service;


import com.qks.photoShare.entity.User;

public interface UserService {

    User ifUserExist(String name, String passsword);
    User getUserById(String id);
    boolean addUser(String id, String name, String password);
    String getUserIdByPostId(String id);
}
