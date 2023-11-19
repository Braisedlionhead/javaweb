package com.ydlclass.filter;

import java.util.ArrayList;
import java.util.List;

import com.ydlclass.core.Container;
import com.ydlclass.core.Filter;
import com.ydlclass.core.HttpRequest;
import com.ydlclass.core.HttpResponse;
import com.ydlclass.core.Session;

public class CharsetFilter implements Filter {

    @Override
    // 未登录， 直接重定向， 登陆了， 返回true
    public Boolean preFilter(HttpRequest request, HttpResponse response) {
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        return true;
    }

    @Override
    public Boolean postFilter(HttpRequest request, HttpResponse response) {
        return true;
    }

}
