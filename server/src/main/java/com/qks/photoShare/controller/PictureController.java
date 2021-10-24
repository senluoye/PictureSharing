package com.qks.photoShare.controller;

import com.qks.photoShare.entity.Picture;
import com.qks.photoShare.service.PictureService;
import com.qks.photoShare.service.PostService;
import com.qks.photoShare.util.JWTUtils;
import com.qks.photoShare.util.MyResponseUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    private final PostService postService;

    public PictureController(JWTUtils jwtUtils, PictureService pictureService, MyResponseUtil myResponseUtil, PostService postService) {
        this.jwtUtils = jwtUtils;
        this.pictureService = pictureService;
        this.myResponseUtil = myResponseUtil;
        this.postService = postService;
    }

    /**
     * 上传一张图片
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Map<String, Object> addPicture(@RequestParam("file") MultipartFile file, @RequestHeader String token) throws IOException {

        String pictureId = UUID.randomUUID().toString();
        try {
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream("files//" + pictureId + ".jpg"));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e){
            e.printStackTrace();
            return myResponseUtil.getResultMap("false", "上传失败", null);
        }

        return myResponseUtil.getResultMap("success", null, pictureId);
    }

    /**
     * 获取一张图片
     * @param token
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "image/jpg")
    private byte[] getPicture(@PathVariable(name = "id") String id) throws IOException {
        File file = new File("./files/" + id + ".jpg");
        FileInputStream inputstream = new FileInputStream(file);
        byte[] bytes = new byte[inputstream.available()];
        inputstream.read(bytes, 0, inputstream.available());
        inputstream.close();
        return bytes;
    }

//    @RequestMapping(value = "{id}", method = RequestMethod.GET)
//    private void getPicture(HttpServletResponse response, @PathVariable(name = "id") String id) throws IOException {
//
//        response.setHeader("Content-Disposition", "attachment;filename=" + id + ".jpg");
//        // 响应类型,编码
//        response.setContentType("application/octet-stream;charset=UTF-8");
//        // 形成输出流
//        OutputStream osOut = response.getOutputStream();
//        File file = new
//                File("./files/" + id + ".jpg");
//        InputStream input = null;
//        try {
//            input = new FileInputStream(file);
//            byte[] buf = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = input.read(buf)) > 0) {
//                osOut.write(buf, 0, bytesRead);
//            }
//        } finally {
//            input.close();
//            osOut.close();
//        }
//    }

}
