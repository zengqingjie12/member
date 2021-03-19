package com.example.demo.mapper;

import com.example.demo.model.PillowTalk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zengqingjie
 * @description 浏览记录mapper
 * @date 2020/1/7 13:43
 **/
@Mapper
@Component
public interface VisitLogMapper {

    /**
     * 添加浏览记录
     * @param visitIp 浏览ip
     * @return
     */
    Integer addVisitLog(@Param("visitIp") String visitIp,@Param("equipment")String equipment);

}
