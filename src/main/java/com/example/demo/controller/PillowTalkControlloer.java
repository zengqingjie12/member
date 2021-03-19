package com.example.demo.controller;

import com.example.demo.model.ApiData;
import com.example.demo.model.PillowTalk;
import com.example.demo.service.PillowTalkService;
import com.example.demo.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zengqingjie
 * @description 留言controller
 * @date 2020/1/7 14:15
 **/
@Controller
@RequestMapping(value = "/pillowTalk")
public class PillowTalkControlloer {

    private static final Logger logger = LoggerFactory.getLogger(PillowTalkControlloer.class);

    @Autowired
    private PillowTalkService pillowTalkService;


    /**
     * 获取留言列表
     * @param pageIndex
     * @param pageSize
     * @param beginTime
     * @param endTime
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getPillowTalkList")
    public Object getPillowTalkList(Integer pageIndex,Integer pageSize,String beginTime,String endTime){
        ApiData apiData = new ApiData();
        if (pageIndex == null){
            pageIndex = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        try {
            List<PillowTalk> list = pillowTalkService.getPillowList(pageIndex, pageSize, beginTime, endTime);
            Integer count = pillowTalkService.getPillowTalkListCount(beginTime, endTime);
            apiData.setData(list);
            apiData.setCount(count);
            apiData.setCode(1);
            apiData.setMsg("查询成功");
            return apiData;
        }catch (Exception e){
            logger.error("pillowTalk getPillowTalkList [" + e.getMessage() + "]", e);
        }
        apiData.setCode(-1);
        apiData.setMsg("查询失败");
        apiData.setData(new ArrayList<>());
        return apiData;
    }

    /**
     * 添加留言
     * @param talkContent 留言内容
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addPillowTalk")
    public Object addPillowTalk(HttpServletRequest request, HttpServletResponse response, String talkContent){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST");        //请求放行，设置其为GET或者POST访问
        ApiData apiData = new ApiData();
        apiData.setData(new HashMap<>());
        if (StringUtils.isBlank(talkContent)){
            apiData.setCode(-1);
            apiData.setMsg("参数错误");
            return apiData;
        }
        String talkIp = IpUtil.getIpAddr(request);
        try {
            Integer flag = pillowTalkService.addPillowTalk(talkContent, talkIp);
            if (flag > 0){
                apiData.setMsg("添加成功");
                apiData.setCode(1);
                return apiData;
            }
        }catch (Exception e){
            logger.error("pillowTalk addPillowTalk [" + e.getMessage() + "]", e);
        }
        apiData.setCode(-1);
        apiData.setMsg("添加失败");
        return apiData;
    }

    /**
     * 删除留言
     * @param talkId 留言id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deletePillowTalk")
    public Object deletePillowTalk(Integer talkId){
        ApiData apiData = new ApiData();
        apiData.setData(new HashMap<>());
        if (talkId == null){
            apiData.setCode(-1);
            apiData.setMsg("参数错误");
            return apiData;
        }
        try {
            Integer flag = pillowTalkService.deletePillowTalk(talkId);
            if (flag > 0){
                apiData.setMsg("删除成功");
                apiData.setCode(1);
                return apiData;
            }
        }catch (Exception e){
            logger.error("pillowTalk deletePillowTalk [" + e.getMessage() + "]", e);
        }
        apiData.setCode(-1);
        apiData.setMsg("删除失败");
        return apiData;
    }
}
