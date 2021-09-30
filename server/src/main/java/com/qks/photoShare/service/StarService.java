package com.qks.photoShare.service;

import com.qks.photoShare.entity.Star;

public interface StarService {

    Star getStarByPostAndUserId(String postId, String userId);
    boolean addStar(String id, String postId, String userId);
    String deleteStar(String postId, String userId);
    Integer getCountByPostId(String id);
}
