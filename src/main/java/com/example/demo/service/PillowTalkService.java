package com.example.demo.service;

import com.example.demo.mapper.PillowTalkMapper;
import com.example.demo.model.PillowTalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengqingjie
 * @description 留言service
 * @date 2020/1/7 13:58
 **/
@Service
public class PillowTalkService {

    @Autowired
    private PillowTalkMapper pillowTalkMapper;

    /**
     * 获取留言列表
     * @param pageIndex 页数
     * @param pageSize 条数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 留言列表
     */
    public List<PillowTalk> getPillowList(Integer pageIndex,Integer pageSize,String beginTime,String endTime){
        Integer offset = (pageIndex -1) * pageSize;
        return this.pillowTalkMapper.getPillowTalkList(offset,pageSize,beginTime,endTime);
    }

    /**
     * 获取留言列表条数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return 留言列表条数
     */
    public Integer getPillowTalkListCount(String beginTime,String endTime){
        return this.pillowTalkMapper.getPillowTalkListCount(beginTime, endTime);
    }

    /**
     * 添加留言
     * @param talkContent 留言内容
     * @param talkIp 留言ip
     * @return
     */
    public Integer addPillowTalk(String talkContent,String talkIp){
        return this.pillowTalkMapper.addPillowTalk(talkContent, talkIp);
    }

    /**
     * 删除留言
     * @param talkId 留言id
     * @return
     */
    public Integer deletePillowTalk(Integer talkId){
        return this.pillowTalkMapper.deletePillowTalk(talkId);
    }



}
