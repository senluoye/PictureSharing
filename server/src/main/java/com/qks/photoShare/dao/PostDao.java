package com.qks.photoShare.dao;

import com.qks.photoShare.entity.Post;

import java.util.List;

public interface PostDao {

    List<Post> getHello();
    List<Post> getHelloByUserId(String userId);
    Integer addPost(String id, String userId, String content, String pictures, long date);
    Integer DeletePostById(String id);
    Post findPostById(String id);
    String getUserIdByPostId(String id);
}
