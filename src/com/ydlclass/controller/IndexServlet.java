package com.ydlclass.controller;

import com.ydlclass.core.Container;
import com.ydlclass.core.HttpRequest;
import com.ydlclass.core.HttpResponse;
import com.ydlclass.core.Servlet;
import com.ydlclass.core.Session;
import com.ydlclass.entity.User;

public class IndexServlet implements Servlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.getHeader("Cookie") != null) {
            // 从Cookie中拿id， 拿自己的柜子
            String cookie = request.getHeader("Cookie");
            String sessionId = cookie.split("=")[1];
            Session session = Container.getSession(sessionId);
            // 在实际代码中还需要做额外判断， 但此处暂不做
            if (session == null || session.get("user") == null) {
                HttpResponse.redirect(response.getOutputStream(), "http://127.0.0.1:8080/login.html");
                return;
            }

            User user = (User) session.get("user");
            HttpResponse.sucess(response.getOutputStream(), "current user:" + user.getUsername());
        } else {
            // 若没有Cookie， 需要做个重定向， 返回登录界面
            HttpResponse.redirect(response.getOutputStream(), "http://127.0.0.1:8080/login.html");
        }

    }
}
