package com.qks.photoShare.controller;

import com.qks.photoShare.entity.Picture;
import com.qks.photoShare.service.PictureService;
import com.qks.photoShare.util.JWTUtils;
import com.qks.photoShare.util.MyResponseUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/picture")
public class PictureController {

    private final JWTUtils jwtUtils;
    private final PictureService pictureService;
    private final MyResponseUtil myResponseUtil;

    public PictureController(JWTUtils jwtUtils, PictureService pictureService, MyResponseUtil myResponseUtil) {
        this.jwtUtils = jwtUtils;
        this.pictureService = pictureService;
        this.myResponseUtil = myResponseUtil;
    }

    /**
     * 上传一张图片
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Map<String, Object> addPicture(@RequestParam("file") MultipartFile file) throws IOException {

        String id = UUID.randomUUID() + "";

        try {
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream("files//" + Objects.requireNonNull(file.getOriginalFilename())));
            System.out.println(file.getName());
            System.out.println(file.getOriginalFilename());
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
            return myResponseUtil.getResultMap("false", "上传失败", null);
        }
            return myResponseUtil.getResultMap("success", null, id);
    }

    /**
     * 获取一张图片
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    private Map<String, Object> getPicture(@RequestHeader String token, @PathVariable(name = "id") String id) {
        Map<String, Object> resultMap = new HashMap<>();

        if (jwtUtils.verify(token)) {
            Picture picture = pictureService.getPicture(id);
            if (picture != null)
                resultMap = myResponseUtil.getResultMap("success", null, picture);
            else
                resultMap = myResponseUtil.getResultMap("false", "不存在这张图片", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);
        return resultMap;
    }
}
