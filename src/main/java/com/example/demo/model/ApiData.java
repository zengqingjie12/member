package com.example.demo.model;

/**
 * @author zengqingjie
 * @description
 * @date 2020/1/7 14:21
 **/
public class ApiData {

    /**返回状态值**/
    private Integer code;

    /**返回提示**/
    private String msg;

    /**条数**/
    private Integer count;

    /**返回数据**/
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

