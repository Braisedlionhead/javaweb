package com.ydlclass.core;

/**
 * 过滤器， 统一处理请求和响应
 */
public interface Filter {

    /**
     * 处理请求前的过滤器
     * 
     * @param request
     * @param response
     * @return Boolean true放行， false不放行
     */
    Boolean preFilter(HttpRequest request, HttpResponse response);

    /**
     * 处理请求后的过滤器
     * 
     * @param request
     * @param response
     * @return
     */
    Boolean postFilter(HttpRequest request, HttpResponse response);
}
