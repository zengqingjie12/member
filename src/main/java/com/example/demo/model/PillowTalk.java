package com.example.demo.model;

import lombok.Data;

/**
 * @author zengqingjie
 * @description 留言
 * @date 2020/1/7 11:22
 **/
@Data
public class PillowTalk {
    /**留言id**/
    private Integer talkId;

    /**留言内容**/
    private String talkContent;

    /**留言IP**/
    private String talkIp;

    /**留言时间**/
    private String createTime;

    /**是否有效 0否1是**/
    private Integer valid;
}
