package com.qks.photoShare.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class testController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    private Map<String, Object> Register() {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "test");
        return map;
    }
}
