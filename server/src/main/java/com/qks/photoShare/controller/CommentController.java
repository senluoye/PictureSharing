package com.qks.photoShare.controller;

import com.qks.photoShare.entity.Comment;
import com.qks.photoShare.service.CommentService;
import com.qks.photoShare.service.NoticeService;
import com.qks.photoShare.service.PostService;
import com.qks.photoShare.service.UserService;
import com.qks.photoShare.util.JWTUtils;
import com.qks.photoShare.util.MyResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;
    private final JWTUtils jwtUtils;
    private final PostService postService;
    private final NoticeService noticeService;
    private final MyResponseUtil myResponseUtil;
    private final UserService userService;

    public CommentController(CommentService commentService, JWTUtils jwtUtils, PostService postService, NoticeService noticeService, MyResponseUtil myResponseUtil, UserService userService) {
        this.commentService = commentService;
        this.jwtUtils = jwtUtils;
        this.postService = postService;
        this.noticeService = noticeService;
        this.myResponseUtil = myResponseUtil;
        this.userService = userService;
    }

//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private JWTUtils jwtUtils;
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private NoticeService noticeService;
//
//    @Autowired
//    private MyResponseUtil myResponseUtil;
//
//    @Autowired
//    private UserService userService;

    /**
     * 增加一个评论
     * @param map
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Map<String, Object> addComment(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;
        if (jwtUtils.verify(token)){
            String content = map.get("content").toString();
            String postId = map.get("postId").toString();
            String userId = jwtUtils.parser(token).get("id").toString();

            if (postService.findPostById(postId) != null){
                String id = UUID.randomUUID() + "";
                if (commentService.addComment(id, postId, content, userId, (new Date()).getTime())){
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", id);
                    data.put("postId", postId);
                    data.put("content", content);
                    data.put("userId", userId);
                    data.put("date", new Date().getTime());
                    resultMap = myResponseUtil.getResultMap("success", null, data);
                    noticeService.addNotice(userId, userService.getUserIdByPostId(postId), postId, "评论了你的动态");
                } else
                    resultMap = myResponseUtil.getResultMap("false", "添加评论失败", null);
            } else
                resultMap = myResponseUtil.getResultMap("false", "该条动态不存在", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);

        return resultMap;
    }

    /**
     * 查看一个动态下的所有评论
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    private Map<String, Object> getList(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;

        if (jwtUtils.verify(token)){
            String postId = map.get("postId").toString();
            if (postService.findPostById(postId) != null){
                List<Comment> commentList = commentService.getCommentListByPostId(postId);
                resultMap = myResponseUtil.getResultMap("success", null, commentList);
            } else {
                resultMap = myResponseUtil.getResultMap("false", "目标动态不存在", null);
            }
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登录", null);

        return resultMap;
    }

    /**
     * 删除某条评论
     * @param token
     * @param map
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    private Map<String, Object> DeleteComment(@RequestHeader String token, @RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;

        if (jwtUtils.verify(token)){
            String postId = map.get("postId").toString();
            if (postService.findPostById(postId) != null){
                String commentId = map.get("id").toString();
                if (commentService.deleteCommentByCommentId(commentId))
                    resultMap = myResponseUtil.getResultMap("success", null, null);
                else
                    resultMap = myResponseUtil.getResultMap("false", "删除评论失败", null);
            } else
                resultMap = myResponseUtil.getResultMap("false", "目标动态不存在", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "验证失败，请重新登陆", null);

        return resultMap;
    }
}
