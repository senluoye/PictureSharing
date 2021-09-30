package com.qks.photoShare.service.Impl;

import com.qks.photoShare.dao.PictureDao;
import com.qks.photoShare.entity.Picture;
import com.qks.photoShare.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureDao pictureDao;

    public PictureServiceImpl(PictureDao pictureDao) {
        this.pictureDao = pictureDao;
    }

    @Override
    public boolean addPicture(String id, String base64) {
        return pictureDao.addPicture(id, base64) > 0;
    }

    @Override
    public Picture getPicture(String id) {
        return pictureDao.getPicture(id);
    }
}
