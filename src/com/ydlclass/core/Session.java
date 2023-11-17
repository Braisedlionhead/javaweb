package com.ydlclass.core;

import java.util.HashMap;

/**
 * 每一个客户端， 都有一个session来存储数据
 */
public class Session extends HashMap<String, Object> {

    public void setAttribute(String key, Object object) {
        super.put(key, object);
    }

    public Object getAttribute(String key) {
        return super.get(key);
    }
}
