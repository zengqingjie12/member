package com.example.demo.mapper;

import com.example.demo.model.PillowTalk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengqingjie
 * @description 留言mapper
 * @date 2020/1/7 13:43
 **/
@Mapper
@Component
public interface PillowTalkMapper {

    /**
     * 获取留言列表
     * @param offset 起始数
     * @param pageSize 条数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    List<PillowTalk> getPillowTalkList(@Param("offset")Integer offset,@Param("pageSize")Integer pageSize,@Param("beginTime")String beginTime,@Param("endTime")String endTime);

    /**
     * 获取留言条数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    Integer getPillowTalkListCount(@Param("beginTime")String beginTime,@Param("endTime")String endTime);

    /**
     * 添加留言
     * @param talkContent 留言内容
     * @param talkIp 留言ip
     * @return
     */
    Integer addPillowTalk(@Param("talkContent")String talkContent,@Param("talkIp")String talkIp);

    /**
     * 删除留言
     * @param talkId 留言id
     * @return
     */
    Integer deletePillowTalk(@Param("talkId")Integer talkId);

}
