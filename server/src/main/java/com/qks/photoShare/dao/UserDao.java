package com.qks.photoShare.dao;

import com.qks.photoShare.entity.User;


public interface UserDao {

    User ifUserExist(String name, String password);
    User getUserById(String id);
    boolean addUser(String id, String name, String password);
    String getUserIdByPostId(String id);
}
