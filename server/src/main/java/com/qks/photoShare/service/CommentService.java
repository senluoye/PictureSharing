package com.qks.photoShare.service;

import com.qks.photoShare.entity.Comment;

import java.util.List;

public interface CommentService {

    boolean addComment(String id, String postId, String content, String userId, long date);
    List<Comment> getCommentListByPostId(String postId);
    boolean deleteCommentByCommentId(String commentId);
}
