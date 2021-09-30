package com.qks.photoShare.dao;

import com.qks.photoShare.entity.Comment;

import java.util.List;

public interface CommentDao {

    boolean addComment(String id, String postId, String content, String userId, long date);
    List<Comment> getCommentListByPostId(String postId);
    boolean deleteCommentByCommentId(String commentId);
}
