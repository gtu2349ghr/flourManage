package com.controller;

import com.entity.Flour;
import com.entity.Page;
import com.jcraft.jsch.ChannelSftp;
import com.mysql.jdbc.util.Base64Decoder;
import com.service.FlourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Controller
public class flourController {
    public static final String PICTUREURL = "D:/nginx-1.13.8/html/slitLamp/screening/";
    public static final String CONFIGUREURL = "http://192.168.117.128:80/";
    @Autowired
    FlourService flourService;

    @RequestMapping("/")
    public String index(){
//        return "index.html";
        return "admin_list_userInfo.html";
    }
    @RequestMapping("/index")
    public  String index1(){
        return  "index.html";
    }
    @RequestMapping("/flour")
    public String flour1(Model model, @RequestParam(value = "currentPage",defaultValue = "1")int currentPage
    ,HttpServletRequest request
    ){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies
             ) {
            if(cookie.getName().equals("user")){
                List<Flour> inqure = flourService.inqure();
                Page page = new Page();
                page.setCurrentPage(currentPage);
                page.setLimitPage(10);
                Page<Flour> page1 = flourService.selectPage(page);
                model.addAttribute("list",page1.getData());
                model.addAttribute("currentPage",currentPage);
                model.addAttribute("TotelPage",page.getTotlePage());
                model.addAttribute("TotelCount",page.getTotelCount());
                return "admin_list_userInfo.html";
            }
        }return "index.html";



    }
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam (value = "id") Integer id){
        System.out.println("进入删除方法");
        HashMap<String, Object> map = new HashMap<>();
        int i = flourService.deleteFlour(id);
        if(i==1){
            map.put("code","1");
        }else {
            map.put("code","0");
        }
        return map;
    }
    @RequestMapping("/selectLike")
    public  String selectLikes(@RequestParam(value = "name" ) String name,Model model ){
        List<Flour> flours = flourService.selectLike(name);
        model.addAttribute("list",flours);
       return    "flourTable.html";

    }
    @RequestMapping("/addFlour")
    @ResponseBody
    public  Map addFlour(@RequestParam(value = "flour") String flour,
                         @RequestParam(value = "price") Double price,
                         @RequestParam(value = "number") Integer number,
                         @RequestParam(value = "img", required = false) MultipartFile file
                         ){

        String url = null;
        // TODO 自动生成的方法存根
        String filename1 = "picture_" + UUID.randomUUID() + ".jpg";//生成唯一图片路径
        if (!file.isEmpty()) {
            try {
                File filepath = new File(PICTUREURL);
                if(!filepath.exists()){
                    filepath.mkdirs();
                }
                // 文件保存路径
                String savePath = PICTUREURL + filename1;
                // 转存文件
                file.transferTo(new File(savePath));
                serverConf serverConf = new serverConf();

                 url = CONFIGUREURL + filename1;
                System.out.println(url);
                ChannelSftp connect = serverConf.connect("192.168.117.128",22,"root","518610");
               serverConf.SendFile("/opt/etc/",savePath,connect);
            } catch (Exception e) {

                e.printStackTrace();
            }

        }

        System.out.println(file);
        Map<String, Object> map = new HashMap<>();
        Flour flour1 = new Flour();
        flour1.setFlour(flour);
        flour1.setPrice(price);
        flour1.setNumber(number);
       flour1.setImage(url);
        int i = flourService.insertFlour(flour1);
         map.put("code", i);
         return map;
    }
    @RequestMapping("selectAll")
    public  String selectAll(Model model){
        List<Flour> flours = flourService.inqure();
        model.addAttribute("list",flours);
        return "flourTable.html";
    }
    @RequestMapping("/updateFlour")
    @ResponseBody
    public Map updateFlour(@RequestParam(value = "id") int id,
                           @RequestParam(value = "flour") String flour,
                           @RequestParam(value = "price") Double price,
                           @RequestParam(value = "number") int number
                           ){
        HashMap<String, Object> map = new HashMap<>();
        Flour flour1 = new Flour();
        flour1.setId(id);
        flour1.setFlour(flour);
        flour1.setNumber(number);
        flour1.setPrice(price);
        int rows=flourService.updateFlour(flour1);
                map.put("code",rows);
        return map;
    }


}
