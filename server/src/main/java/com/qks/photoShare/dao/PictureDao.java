package com.qks.photoShare.dao;

import com.qks.photoShare.entity.Picture;

public interface PictureDao {
    Integer addPicture(String id, String base64);
    Picture getPicture(String id);
}
