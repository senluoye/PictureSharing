package com.qks.photoShare.dao;

import com.qks.photoShare.entity.Notice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public interface NoticeDao {

    List<Notice> getAllNoticeByReceiverId(String receiverId);
    Notice getNoticeById(String id);
    void changeReadById(String id);
    void addNotice(String id, String sender, String receiver, String postId, String content, boolean read, long date);
}
