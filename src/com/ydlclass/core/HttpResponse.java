package com.ydlclass.core;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装一个响应对象
 */
public class HttpResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    // 协议
    private String protocol = "Http/1.1";
    // 响应码
    private String code = "200";
    // 信息
    private String message = "ok";
    // 响应头
    private Map<String, String> header = new HashMap<>();
    // 响应体
    private String body;
    // 持有一个流
    private OutputStream outputStream;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeader(String key) {
        return this.header.get(key);
    }

    public void setHeader(String key, String value) {
        this.header.put(key, value);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    // 拿到所有响应头
    public Map<String, String> getHeaders() {
        return this.header;
    }

    // 为了以后方便使用， 构建一个成功的响应
    public static void sucess(OutputStream outputStream, String msg) {
        HttpResponse httpResponse = new HttpResponse();
        String body = "<h1>" + msg + "</h1>";
        httpResponse.setHeader("Content-Type", "text/html;charset=UTF-8");
        httpResponse.setHeader("Content-Length", Integer.toString(body.getBytes().length));
        httpResponse.setBody(body);
        HttpResponseHandler.write(outputStream, httpResponse);

    }

    // 为了以后方便使用， 构建一个fail的响应
    // 通信成功(请求和响应成功)， 但是某些业务出了问题
    public static void fail(OutputStream outputStream, String msg) {
        HttpResponse httpResponse = new HttpResponse();
        String body = "<h1>" + msg + "</h1>";
        httpResponse.setHeader("Content-Type", "text/html;charset=UTF-8");
        httpResponse.setHeader("Content-Length", Integer.toString(body.getBytes().length));
        httpResponse.setBody(body);
        HttpResponseHandler.write(outputStream, httpResponse);

    }

    // 为了以后方便使用， 构建一个error的响应
    // 通信失败
    public static void error(OutputStream outputStream, String msg) {
        HttpResponse httpResponse = new HttpResponse();
        String body = "<h1>" + msg + "</h1>";
        httpResponse.setCode("500");
        httpResponse.setMessage("Internal Server Error");
        httpResponse.setHeader("Content-Type", "text/html;charset=UTF-8");
        httpResponse.setHeader("Content-Length", Integer.toString(body.getBytes().length));
        httpResponse.setBody(body);
        HttpResponseHandler.write(outputStream, httpResponse);

    }

}
