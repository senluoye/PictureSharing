package com.qks.photoShare.controller;

import com.qks.photoShare.entity.User;
import com.qks.photoShare.service.UserService;
import com.qks.photoShare.util.JWTUtils;
import com.qks.photoShare.util.MyResponseUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JWTUtils jwtUtils;
    private final MyResponseUtil myResponseUtil;

    public UserController(UserService userService, JWTUtils jwtUtils, MyResponseUtil myResponseUtil) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.myResponseUtil = myResponseUtil;
    }

    /**
     * 注册
     * @return map
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    private Map<String, Object> Register(@RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;
        String name = map.get("name").toString();
        String password = map.get("password").toString();

        if (this.userService.ifUserExist(name, password) == null){
            String id = UUID.randomUUID() + "";
            if (this.userService.addUser(id, name, password)){
                resultMap = myResponseUtil.getResultMap("success", null, null);
            } else
                resultMap = myResponseUtil.getResultMap("false", "注册用户失败", null);
        } else
            resultMap = myResponseUtil.getResultMap("false", "该用户已存在", null);

        return resultMap;
    }

    /**
     * 登陆
     * @return map
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    private Map<String, Object> Login(@RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap;
        Map<String, Object> userMap = new HashMap<>();
        String name = map.get("name").toString();
        String password = map.get("password").toString();

        User user = userService.ifUserExist(name, password);
        if (user != null){
            userMap.put("id", user.getId());
            userMap.put("name", name);
            Map<String, Object> data = new HashMap<>();
            data.put("token", jwtUtils.createToken(userMap));
            resultMap = myResponseUtil.getResultMap("success", null, data);
        } else
            resultMap = myResponseUtil.getResultMap("false", "该用户不存在或密码错误", null);

        return resultMap;
    }
}
