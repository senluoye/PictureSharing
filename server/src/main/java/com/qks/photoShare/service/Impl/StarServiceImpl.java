package com.qks.photoShare.service.Impl;

import com.qks.photoShare.dao.StarDao;
import com.qks.photoShare.entity.Star;
import com.qks.photoShare.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StarServiceImpl implements StarService {

    @Resource
    private StarDao starDao;

    @Override
    public Integer getCountByPostId(String id) {
        return starDao.getCountByPostId(id);
    }

    @Override
    public Star getStarByPostAndUserId(String postId, String userId) {
        return starDao.getStarByPostAndUserId(postId, userId);
    }

    @Override
    public boolean addStar(String id, String postId, String userId) {
        return starDao.addStar(id, postId, userId) > 0;
    }

    @Override
    public String deleteStar(String postId, String userId) {
        if (starDao.deleteStar(postId, userId) > 0)
            return starDao.getStarByPostAndUserId(postId, userId).getId();
        else
            return null;
    }
}
