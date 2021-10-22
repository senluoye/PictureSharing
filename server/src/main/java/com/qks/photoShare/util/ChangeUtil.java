package com.qks.photoShare.util;

import com.qks.photoShare.dao.PostDao;
import com.qks.photoShare.dao.UserDao;
import com.qks.photoShare.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChangeUtil {

    @Autowired
    private UserDao userDao;

    public List<Map<String, Object>> changePost(List<Post> list){
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Post post : list) {
            Map<String, Object> data = new HashMap<>();
            String[] str = post.getPictures().split(",");
            String id = post.getUserId();
            String username = userDao.getUserById(id).getName();

            data.put("id", post.getId());
            data.put("content", post.getContent());
            data.put("userId", id);
            data.put("username", username);
            data.put("pictures", Arrays.asList(str));
            data.put("date", post.getDate());

            resultList.add(data);
        }
        return resultList;
    }

}
