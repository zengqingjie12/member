package com.example.demo.controller;

import com.example.demo.service.VisitLogService;
import com.example.demo.utils.IpUtil;
import com.example.demo.utils.PhoneUtil;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengqingjie
 * @description
 * @date 2019/8/23 15:04
 **/
@Controller
@RequestMapping(value = "/")
public class BeginController {

    public static String android="Android";
    public static String iphone="iPhone";
    public static String ipad="iPad";
    public static String noDevice="未知设备";


    @Autowired
    private VisitLogService visitLogService;

    @ResponseBody
    @RequestMapping(value = "/one")
    public Object begin(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("begin","beginOne");
        return map;
    }

    @RequestMapping(value = "/two")
    public String two(HttpServletRequest request){
        return "two";
    }

    @RequestMapping(value = "/three")
    public String three(HttpServletRequest request){
        return "one";
    }

    @RequestMapping(value = "/four")
    public String four(HttpServletRequest request){
        request.setAttribute("abc","abc");
        return "four";
    }

    /**
     * 访问主页
     * @param request
     * @return
     */
    @RequestMapping(value = "/")
    public String index(HttpServletRequest request, HttpServletResponse response){
        String visitIp = IpUtil.getIpAddr(request);
        String ua = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        OperatingSystem os1 = userAgent.getOperatingSystem();
        String system = os1.getName();
        String a = getPhoneModel(ua);
        visitLogService.addVisitLog(visitIp,system+ "|" + a);


        if(PhoneUtil.checkAgentIsMobile(ua)){
            return "photo/index";
        }
        return "index";
    }

    //获取移动用户操作系统
    public static String getMobileOS(String userAgent){
        if (userAgent.contains(android)) {
            return android;
        }else if (userAgent.contains(iphone)){
            return iphone;
        }else if (userAgent.contains(ipad)){
            return ipad;
        }else {
            return "others";
        }
    }

    //获取用户手机型号
    public static String getPhoneModel(String userAgent){

        if(null == userAgent || "" == userAgent) return noDevice;

        String OS=getMobileOS(userAgent);
        if (OS.equals(android)) {
            String rex="[()]+";
            String[] str=userAgent.split(rex);
            str = str[1].split("[;]");
            String[] res=str[str.length-1].split("Build/");
            return res[0];
        }else if (OS.equals(iphone)) {
            String[] str=userAgent.split("[()]+");
            String res="iphone"+str[1].split("OS")[1].split("like")[0];
            return res;
        }else if (OS.equals(ipad)) {
            return ipad;
        }else {
            return null;
        }
    }


}
