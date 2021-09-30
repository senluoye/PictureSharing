package com.qks.photoShare.service.Impl;

import com.qks.photoShare.dao.CommentDao;
import com.qks.photoShare.entity.Comment;
import com.qks.photoShare.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public boolean addComment(String id, String postId, String content, String userId, long date) {
        return commentDao.addComment(id, postId, content, userId, date);
    }

    @Override
    public List<Comment> getCommentListByPostId(String postId) {
        return commentDao.getCommentListByPostId(postId);
    }

    @Override
    public boolean deleteCommentByCommentId(String commentId) {
        return commentDao.deleteCommentByCommentId(commentId);
    }
}
