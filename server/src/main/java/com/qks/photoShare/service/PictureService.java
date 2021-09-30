package com.qks.photoShare.service;

import com.qks.photoShare.entity.Picture;

public interface PictureService {

    boolean addPicture(String id, String base64);
    Picture getPicture(String id);
}
