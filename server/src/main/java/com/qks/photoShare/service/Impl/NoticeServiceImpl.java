package com.qks.photoShare.service.Impl;

import com.qks.photoShare.dao.NoticeDao;
import com.qks.photoShare.entity.Notice;
import com.qks.photoShare.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDao noticeDao;

    public NoticeServiceImpl(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @Override
    public List<Notice> getAllNoticeByReceiverId(String receiverId) {
        return noticeDao.getAllNoticeByReceiverId(receiverId);
    }

    @Override
    public Notice getNoticeById(String id) {
        return noticeDao.getNoticeById(id);
    }

    @Override
    public void changeReadById(String id) {
        noticeDao.changeReadById(id);
    }

    @Override
    public void addNotice(String sender, String receiver, String postId, String content) {
        String id = UUID.randomUUID().toString();
        noticeDao.addNotice( id, sender, receiver, postId, content, false, (new Date()).getTime());
//        return id;
    }
}
