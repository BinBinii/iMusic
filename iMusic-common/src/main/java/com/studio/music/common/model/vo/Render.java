package com.studio.music.common.model.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: BinBin
 * @Date: 2022/09/07/10:19
 * @Description: 响应结果
 */
public class Render<T> implements Serializable {
    private String msg;

    private T data;

    private int code;

    private Boolean error;

    private Long timestamp;

    private static Map map;


    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public Boolean getError() {
        return error;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public static <T> Render<T> fail(String message) {
        Render<T> msg = new Render<>();
        msg.msg = message;
        msg.code(ResultCode.EXCEPTION.val());
        msg.error(true);
        return msg.putTimeStamp();
    }

    public static <T> Render<T> fail(ResultCode resultCode) {
        Render<T> msg = new Render<>();
        msg.msg = resultCode.msg();
        msg.code(resultCode.val());
        msg.error(true);
        return msg.putTimeStamp();
    }

    public static <T> Render<T> ok() {
        return ok(null);
    }

    private Render<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public static <T> Render<T> ok(T data) {
        return new Render<T>()
                .data(data)
                .putTimeStamp()
                .error(false)
                .msg(ResultCode.SUCCESS.msg())
                .code(ResultCode.SUCCESS.val());
    }

    public static <T> Render<T> ok(T data, String msg) {
        return new Render<T>()
                .data(data)
                .putTimeStamp()
                .error(false)
                .msg(msg)
                .code(ResultCode.SUCCESS.val());
    }

    public static Map okMap(Object data) {
        map = new HashMap();
        map.put("data", data);
        map.put("error", false);
        map.put("code", ResultCode.SUCCESS.val());
        map.put("msg", ResultCode.SUCCESS.msg());
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }

    public static Map failMap(String msg) {
        map = new HashMap();
        map.put("data", null);
        map.put("error", true);
        map.put("code", ResultCode.EXCEPTION.val());
        map.put("msg", msg);
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }


    public static Map failMap(ResultCode resultCode) {
        map = new HashMap();
        map.put("data", null);
        map.put("error", true);
        map.put("code", resultCode.val());
        map.put("msg", resultCode.msg());
        map.put("timestamp", System.currentTimeMillis());
        return map;
    }

    public Render<T> data(T data) {
        this.data = data;
        return this;
    }

    public Render<T> code(int code) {
        this.code = code;
        return this;
    }

    public Render<T> error(Boolean error) {
        this.error = error;
        return this;
    }

    public Render<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

}
