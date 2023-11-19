package com.ydlclass.filter;

import java.util.ArrayList;
import java.util.List;

import com.ydlclass.core.Container;
import com.ydlclass.core.Filter;
import com.ydlclass.core.HttpRequest;
import com.ydlclass.core.HttpResponse;
import com.ydlclass.core.Session;

public class LoginFilter implements Filter {

    @Override
    // 未登录， 直接重定向， 登陆了， 返回true
    public Boolean preFilter(HttpRequest request, HttpResponse response) {

        // 不进行过滤的url
        List<String> whiteNames = new ArrayList<>();
        whiteNames.add("/login.do");
        whiteNames.add("/register.do");
        whiteNames.add("/login.html");
        whiteNames.add("/register.html");
        if (whiteNames.contains(request.getUri())) {
            return true;
        }

        if (request.getHeader("Cookie") != null) {
            // 从Cookie中拿id， 拿自己的柜子
            String cookie = request.getHeader("Cookie");
            String sessionId = cookie.split("=")[1];
            Session session = Container.getSession(sessionId);
            // 在实际代码中还需要做额外判断， 但此处暂不做
            if (session == null || session.get("user") == null) {
                HttpResponse.redirect(response, "http://127.0.0.1:8080/login.html");
                return false;
            }
            // 已登录
            return true;
        } else {
            // 若没有Cookie， 需要做个重定向， 返回登录界面
            HttpResponse.redirect(response, "http://127.0.0.1:8080/login.html");
            return false;
        }
    }

    @Override
    public Boolean postFilter(HttpRequest request, HttpResponse response) {
        return true;
    }

}
