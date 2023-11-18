package com.ydlclass.controller;

import com.ydlclass.core.Container;
import com.ydlclass.core.HttpRequest;
import com.ydlclass.core.HttpResponse;
import com.ydlclass.core.Servlet;
import com.ydlclass.core.Session;
import com.ydlclass.dao.UserDao;
import com.ydlclass.entity.User;

/*
 * 登录的业务逻辑
 */
public class LoginServlet implements Servlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            HttpResponse.fail(response.getOutputStream(), "can not find user");
        } else {
            // 用户存在， 密码相等
            if (user.getPassword() != null && user.getPassword().equals(password)) {
                HttpResponse.sucess(response.getOutputStream(), "successfully login");
                if (request.getHeader("Cookie") != null) {
                    // 从Cookie中拿id， 拿自己的柜子
                    String cookie = request.getHeader("Cookie");
                    String sessionId = cookie.split("=")[1];
                    Session session = Container.getSession(sessionId);
                    session.setAttribute("user", user);
                }

            } else {
                HttpResponse.fail(response.getOutputStream(), "wrong password");
            }
        }
    }

}
