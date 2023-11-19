package com.ydlclass.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServletContainer {

    // Configuration中的SERVLETS是配置， 此处的是具体的容器
    private static final Map<String, Servlet> SERVLETS = new ConcurrentHashMap<>(16);

    public static void setServlet(String key, Servlet servlet) {
        SERVLETS.put(key, servlet);
    }

    // get方法， 让他在获取的时候再加载
    // key就是url
    public Servlet get(String key) {
        // 对这个方法做扩展
        // 获取的时候， 先看看有没有
        Servlet servlet = SERVLETS.get(key);
        if (servlet == null) {
            // 加载这个servlet
            Map<String, String> servlets = Configuration.getSERVLETS();
            String className = servlets.get(key);
            try {
                servlet = (Servlet) Class.forName(className).newInstance();
                SERVLETS.put(key, servlet);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return SERVLETS.get(key);
    }

}
