package com.example.demo.service;

import com.example.demo.mapper.VisitLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author zengqingjie
 * @description 浏览记录service
 * @date 2020/1/7 13:58
 **/
@Service
public class VisitLogService {

    @Autowired
    private VisitLogMapper visitLogMapper;


    /**
     * 添加浏览记录
     * @param visitIp 浏览ip
     * @return
     */
    public Integer addVisitLog(String visitIp,String equipment){
        return this.visitLogMapper.addVisitLog(visitIp,equipment);
    }


}
