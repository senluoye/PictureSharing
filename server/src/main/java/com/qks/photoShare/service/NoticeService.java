package com.qks.photoShare.service;

import com.qks.photoShare.entity.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeService {

    List<Notice> getAllNoticeByReceiverId(String receiverId);
    Notice getNoticeById(String id);
    void changeReadById(String id);
    void addNotice(String sender, String receiver, String postId, String content);
}
