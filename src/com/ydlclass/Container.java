package com.ydlclass;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Container {

    // 未来可能有多个线程来访问， 故设为 ConcurrentHashMap
    /*
     * 存放了大量uri和servlet的对应关系
     */
    private static final Map<String, Servlet> SERVLETS = new ConcurrentHashMap<>(8);

    // 程序启动时， 就把部分内容加载进去
    // 启动的时候注册
    static {
        SERVLETS.put("/user", new UserServlet());
        SERVLETS.put("/login", new LoginServlet());
        SERVLETS.put("/register", new RegisterServlet());
    }

    public static Servlet getServlet(String uri) {
        return SERVLETS.get(uri);
    }

}
