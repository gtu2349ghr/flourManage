package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@Controller
@RequestMapping("/user")
public class userController {
    @Autowired
    UserService userService;
    @RequestMapping("logindo")
    @ResponseBody
    public Map login1(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password,
                      HttpServletRequest request,
                      HttpServletResponse response
                         ){
        System.out.println(username);
        System.out.println(password);
        HashMap<String, Object> map1 = new HashMap<>();
        int rows = userService.login(username, password);
        if(rows==1) {
            Cookie cookie = new Cookie("user",username);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        }
        System.out.println(rows);
         map1.put("code",rows);
         return map1;
    }
    @RequestMapping("register")
    public String register(){
        return"register.html";
    }
    @RequestMapping("registerdo")
    @ResponseBody
    public Map register(@RequestParam(value = "username") String username,
                      @RequestParam(value = "password") String password
    ){
        HashMap<String, Object> map1 = new HashMap<>();
        int rows=userService.register(username,password);
        System.out.println(rows);
        map1.put("code",rows);
        return map1;
    }


}
