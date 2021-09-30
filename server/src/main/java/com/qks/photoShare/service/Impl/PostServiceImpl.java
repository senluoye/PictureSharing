package com.qks.photoShare.service.Impl;

import com.qks.photoShare.dao.PostDao;
import com.qks.photoShare.entity.Post;
import com.qks.photoShare.service.PostService;
import com.qks.photoShare.util.ChangeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostDao postDao;

    private final ChangeUtil changeUtil;

    public PostServiceImpl(ChangeUtil changeUtil) {
        this.changeUtil = changeUtil;
    }

    public List<Map<String, Object>> getHello(){
        return changeUtil.changePost(postDao.getHello());
    }

    public List<Map<String, Object>> getHelloByUserId(String userId){
        List<Post> list = postDao.getHelloByUserId(userId);
        return changeUtil.changePost(list);
    }

    @Override
    public boolean addPost(String id, String userId, String content, String pictures, long date) {
        return postDao.addPost(id, userId, content, pictures, date) > 0;
    }

    @Override
    public boolean DeletePostById(String id) {
        return postDao.DeletePostById(id) > 0;
    }

    @Override
    public Post findPostById(String id) {
        return postDao.findPostById(id);
    }

}
