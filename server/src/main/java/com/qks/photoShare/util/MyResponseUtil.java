package com.qks.photoShare.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyResponseUtil {

    public Map<String, Object> getResultMap(String msg, String error, Object data){
        Map<String, Object> resultMap = new HashMap<>();
        if (error == null){
            resultMap.put("data", data);
            resultMap.put("code", 0);
            resultMap.put("msg", msg);
        } else {
            resultMap.put("code", -1);
            resultMap.put("msg", msg);
            resultMap.put("error", error);
        }
        return resultMap;
    }
    public Map<String, Object> getResultMap(String msg, String error, String operation, Object data){
        Map<String, Object> resultMap = new HashMap<>();
        if (error == null){
            resultMap.put("data", data);
            resultMap.put("code", 0);
            resultMap.put("msg", msg);
            resultMap.put("operation", operation);
        } else {
            resultMap.put("code", -1);
            resultMap.put("msg", msg);
            resultMap.put("error", error);
        }
        return resultMap;
    }

}
