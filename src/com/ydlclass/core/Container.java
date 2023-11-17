package com.ydlclass.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.ydlclass.controller.LoginServlet;
import com.ydlclass.controller.RegisterServlet;
import com.ydlclass.controller.UserServlet;

public class Container {

    // 未来可能有多个线程来访问， 故设为 ConcurrentHashMap
    /*
     * 存放了大量uri和servlet的对应关系
     */
    private static final Map<String, Servlet> SERVLETS = new ConcurrentHashMap<>(8);

    // 会话， 每个session中都能够保存一些信息
    /**
     * 每一个客户端的session数据
     */
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>(8);

    // 程序启动时， 就把部分内容加载进去
    // 启动的时候注册
    static {
        // 加载配置文件
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("servlet.properties"));
            for (Map.Entry entry : properties.entrySet()) {
                //
                Servlet servlet;
                try {
                    // 此处是反射！
                    servlet = (Servlet) Class.forName((String) entry.getValue()).newInstance();
                    SERVLETS.put((String) entry.getKey(), servlet);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Servlet getServlet(String uri) {
        return SERVLETS.get(uri);
    }

    public static void addSession(String key, Session session) {
        SESSIONS.put(key, session);
    }

    public static Session getSession(String key) {
        return SESSIONS.get(key);
    }

}
