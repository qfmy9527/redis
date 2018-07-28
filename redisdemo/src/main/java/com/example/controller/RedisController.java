package com.example.controller;


import com.example.config.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@EnableRedisHttpSession
@RestController
public class RedisController {

    //设置session
    @RequestMapping("/setSession")
    public String setSession(HttpServletRequest quest) {
        quest.getSession().setAttribute("username", "刘平123");
        return null;
    }

    //获取存入redis的数据
    @RequestMapping("/getSession")
    public Map<String, String> getSession(HttpServletRequest quest) throws Exception {
        String username = (String) quest.getSession().getAttribute("username");
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        System.out.println(map);
        return map;
    }
}
