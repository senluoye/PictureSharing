package com.qks.photoShare.service;

import com.qks.photoShare.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PostService {

    List<Map<String, Object>> getHello();
    List<Map<String, Object>> getHelloByUserId(String userId);
    boolean addPost(String id, String userId, String content, String pictures, long date);
    boolean DeletePostById(String id);
    Post findPostById(String id);
}
