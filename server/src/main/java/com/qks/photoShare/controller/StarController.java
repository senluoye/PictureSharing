package com.qks.photoShare.controller;

import com.qks.photoShare.entity.Notice;
import com.qks.photoShare.entity.Star;
import com.qks.photoShare.service.NoticeService;
import com.qks.photoShare.service.PostService;
import com.qks.photoShare.service.StarService;
import com.qks.photoShare.service.UserService;
import com.qks.photoShare.util.JWTUtils;
import com.qks.photoShare.util.MyResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/star")
public class StarController {

    private final JWTUtils jwtUtils;

    private final PostService postService;

    private final StarService starService;

    private final NoticeService noticeService;

    private final MyResponseUtil myResponseUtil;

    private final UserService userService;

    public StarController(MyResponseUtil myResponseUtil, JWTUtils jwtUtils, PostService postService, StarService starService, NoticeService noticeService, UserService userService) {
        this.myResponseUtil = myResponseUtil;
        this.jwtUtils = jwtUtils;
        this.postService = postService;
        this.starService = starService;
        this.noticeService = noticeService;
        this.userService = userService;
    }

    /**
     * 点赞一个动态
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Map<String, Object> starOr(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap = null;

        if (jwtUtils.verify(token)){
            String userId = map.get("userId").toString();
            String postId = map.get("postId").toString();
            String myId = jwtUtils.parser(token).get("id").toString();

            if (postService.findPostById(postId) != null){
                Star star = starService.getStarByPostAndUserId(postId, userId);
                if (star == null){ // 没有点赞
                    String id = UUID.randomUUID() + "";
                    if (starService.addStar(id, postId, userId)){
                        noticeService.addNotice(myId, userId, postId, "点赞了你的动态");

                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put("id", id);
                        dataMap.put("postId", postId);
                        dataMap.put("userId", userId);

                        resultMap = myResponseUtil.getResultMap("success", null, "addStar", dataMap);
                    }
                } else {
                    String id = starService.deleteStar(postId, userId);
                    if (id != null){
                        Map<String, Object> dataMap = new HashMap<>();

                        dataMap.put("id", id);
                        dataMap.put("postId", postId);
                        dataMap.put("userId", userId);

                        resultMap = myResponseUtil.getResultMap("success", null, "deleteStar", dataMap);
                    } else
                        resultMap = myResponseUtil.getResultMap("success", "取消点赞失败", "deleteStar", null);
                }
            } else
                resultMap = myResponseUtil.getResultMap("false", "此条动态不存在", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);

        return resultMap;
    }

    /**
     * 获取点赞数
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "count", method = RequestMethod.POST)
    private Map<String, Object> getStarCount(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;

        if (jwtUtils.verify(token)){
            String postId = map.get("postId").toString();
            if (postService.findPostById(postId) != null){
                Map<String, Object> data = new HashMap<>();
                data.put("count", starService.getCountByPostId(postId));

                resultMap = myResponseUtil.getResultMap("success", null, data);
            } else
                resultMap = myResponseUtil.getResultMap("false", "此条动态不存在", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);

        return resultMap;
    }
}
