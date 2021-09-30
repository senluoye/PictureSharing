package com.qks.photoShare.dao;

import com.qks.photoShare.entity.Star;

public interface StarDao {

    Star getStarByPostAndUserId(String postId, String userId);
    Integer addStar(String id, String postId, String userId);
    Integer deleteStar(String postId, String userId);
    Integer getCountByPostId(String id);
}
