package com.ydlclass.controller;

import com.ydlclass.core.HttpRequest;
import com.ydlclass.core.HttpResponse;
import com.ydlclass.core.Servlet;
import com.ydlclass.dao.UserDao;
import com.ydlclass.entity.User;

/*
 * 注册的业务逻辑
 * 复杂的业务忽略掉(判断用户是否存在)， 向数据库插入数据
 */
public class RegisterServlet implements Servlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        try {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserDao userDao = new UserDao();
            User user = userDao.findUserByUsername(username);
            // 手动添加异常 int i = 1/0;
            // 用户不存在， 可以注册
            if (user == null) {
                userDao.insertUser(new User(1, username, password)); // id在表中会自增， 故不严谨的情况下可以随便设置
                HttpResponse.sucess(response.getOutputStream(), "注册成功！");
            } else {
                // 用户已经存在
                HttpResponse.fail(response.getOutputStream(), "注册失败！");
                // 之后可以跳转到登录界面， 让其进行登录
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpResponse.error(response.getOutputStream(), "有异常发生！");
        }

    }
}
