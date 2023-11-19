package com.ydlclass.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Container {

    // 未来可能有多个线程来访问， 故设为 ConcurrentHashMap
    /*
     * 存放了大量uri和servlet的对应关系
     */
    private static final ServletContainer SERVLETS = new ServletContainer();

    // 会话， 每个session中都能够保存一些信息
    /**
     * 每一个客户端的session数据
     */
    private static final Map<String, Session> SESSIONS = new ConcurrentHashMap<>(8);

    /**
     * 每一个客户端的session数据
     */
    public static final List<Filter> FILTERS = new CopyOnWriteArrayList<>();

    // 初始化FILTERS
    static {
        List<String> filters = Configuration.getFilters();
        for (String filter : filters) {
            try {
                Filter filt = (Filter) Class.forName(filter).newInstance();
                FILTERS.add(filt);
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
