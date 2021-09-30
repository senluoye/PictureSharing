package com.qks.photoShare.service.Impl;

import com.qks.photoShare.dao.UserDao;
import com.qks.photoShare.entity.User;
import com.qks.photoShare.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User ifUserExist(String name, String passsword) {
        return userDao.ifUserExist(name, passsword);
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public boolean addUser(String id, String name, String password) {
        return userDao.addUser(id, name, password);
    }

    @Override
    public String getUserIdByPostId(String id) {
        return userDao.getUserIdByPostId(id);
    }
}

