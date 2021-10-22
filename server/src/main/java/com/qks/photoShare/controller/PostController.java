package com.qks.photoShare.controller;

import com.qks.photoShare.entity.Post;
import com.qks.photoShare.service.PostService;
import com.qks.photoShare.service.UserService;
import com.qks.photoShare.util.JWTUtils;
import com.qks.photoShare.util.MyResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final MyResponseUtil myResponseUtil;

    public PostController(PostService postService, UserService userService, JWTUtils jwtUtils, MyResponseUtil myResponseUtil) {
        this.postService = postService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.myResponseUtil = myResponseUtil;
    }

    /**
     * 获取首页动态列表
     * @return map
     */
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    private Map<String, Object> Hello(@RequestHeader String token) {
        Map<String, Object> resultMap;

        List<Map<String, Object>> list = postService.getHello();
        System.out.println(list.toString());

        if (jwtUtils.verify(token))
            resultMap = myResponseUtil.getResultMap("success", null, list);
        else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);

        return resultMap;
    }

    /**
     * 获取某个人的动态
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "profile", method = RequestMethod.GET)
    private Map<String, Object> Profile(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;
        String userId = map.get("userId").toString();

        if (jwtUtils.verify(token)) {
            if (userService.getUserById(userId) == null)
                resultMap = myResponseUtil.getResultMap("false", "目标用户不存在", null);
            else
                resultMap = myResponseUtil.getResultMap("success", null, postService.getHelloByUserId(userId));
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);

        return resultMap;
    }

    /**
     * 发布新动态
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Map<String, Object> addPost(@RequestHeader String token, @RequestBody Map<String, Object> map) {

        System.out.println(map.toString());

        Map<String, Object> resultMap = new HashMap<>();

        System.out.println("token:" + token);
        if (jwtUtils.verify(token)) {
            String id = UUID.randomUUID().toString();
            System.out.println("id:" + id);

            String userId = jwtUtils.parser(token).get("id").toString();
            System.out.println("userId:" + userId);

            String content = map.get("content").toString();
            String pictures = map.get("pictures").toString();
            long date = new Date().getTime();

            if (postService.addPost(id, userId, content, pictures, date)) {
                System.out.println("插入成功");
                Map<String, Object> data = new HashMap<>();
                data.put("id", id);
                resultMap = myResponseUtil.getResultMap("success", null, data);
            }
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);

        return resultMap;
    }

    /**
     * 删除某条动态
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    private Map<String, Object> Delete(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;
        String id = map.get("id").toString();

        if (jwtUtils.verify(token)) {
            if (postService.findPostById(id) != null) {
                if (postService.DeletePostById(id))
                    resultMap = myResponseUtil.getResultMap("success", null, null);
                else
                    resultMap = myResponseUtil.getResultMap("false", "删除动态失败", null);
            } else
                resultMap = myResponseUtil.getResultMap("false", "不存在此条动态", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);

        return resultMap;
    }

    /**
     * 单独获取某个动态信息
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    private Map<String, Object> GetOnePost(@RequestHeader String token, @PathVariable(name = "id") String id) {
        Map<String, Object> resultMap;
        if (jwtUtils.verify(token)) {
            Post post = postService.findPostById(id);
            if (post != null)
                resultMap = myResponseUtil.getResultMap("success", null, post);
            else
                resultMap = myResponseUtil.getResultMap("false", "不存在此条动态", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登录", null);

        return resultMap;
    }
}
