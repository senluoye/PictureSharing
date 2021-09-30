package com.qks.photoShare.controller;

import com.qks.photoShare.entity.Notice;
import com.qks.photoShare.entity.User;
import com.qks.photoShare.service.NoticeService;
import com.qks.photoShare.service.PostService;
import com.qks.photoShare.service.UserService;
import com.qks.photoShare.util.JWTUtils;
import com.qks.photoShare.util.MyResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final JWTUtils jwtUtils;
    private final MyResponseUtil myResponseUtil;

    public NoticeController(NoticeService noticeService, JWTUtils jwtUtils, MyResponseUtil myResponseUtil) {
        this.noticeService = noticeService;
        this.jwtUtils = jwtUtils;
        this.myResponseUtil = myResponseUtil;
    }

    /**
     * 获取自己的所有通知
     * @param token
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    private Map<String, Object> All(@RequestHeader String token) {
        Map<String, Object> resultMap;

        if (jwtUtils.verify(token)){
            String receiverId = jwtUtils.parser(token).get("id").toString();
            List<Notice> noticeList = noticeService.getAllNoticeByReceiverId(receiverId);
            resultMap = myResponseUtil.getResultMap("success", null, noticeList);
        } else
            resultMap = myResponseUtil.getResultMap("false", "请重新登陆", null);


        return resultMap;
    }

    /**
     * 某个通知变为已读
     * @param token
     * @return
     */
    @RequestMapping(value = "read", method = RequestMethod.POST)
    private Map<String, Object> ReadPost(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;

        if (jwtUtils.verify(token)){
            String id = map.get("id").toString();
            if (noticeService.getNoticeById(id) != null){
                noticeService.changeReadById(id);
                resultMap = myResponseUtil.getResultMap("success", null, null);
            } else
                resultMap = myResponseUtil.getResultMap("false", "该条通知不存在", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "请重新登陆", null);

        return resultMap;
    }
}
