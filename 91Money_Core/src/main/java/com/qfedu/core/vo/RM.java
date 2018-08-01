package com.qfedu.core.vo;

import java.util.HashMap;
import java.util.Map;

/**
 *@Author feri
 *@Date Created in 2018/7/31 00:13
 */
public class RM  extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public RM() {
        put("code", 0);
    }

    public static RM error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static RM error(String msg) {
        return error(500, msg);
    }

    public static RM error(int code, String msg) {
        RM r = new RM();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static RM ok(String msg) {
        RM r = new RM();
        r.put("msg", msg);
        return r;
    }

    public static RM ok(Map<String, Object> map) {
        RM r = new RM();
        r.putAll(map);
        return r;
    }

    public static RM ok() {
        return new RM();
    }

    @Override
    public RM put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
