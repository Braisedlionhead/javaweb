package com.ydlclass.core;

// 接口中的方法默认就是public
public interface Servlet {

    /**
     * 用来根据请求， 处理响应
     * 
     * @param request
     * @param response
     */
    void service(HttpRequest request, HttpResponse response);
}
