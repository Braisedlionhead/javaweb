package com.ydlclass;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 将接收的请求报文转化为请求对象
 */
public class HttpRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String protocol;
    // 请求方式
    private String method;
    // uri
    private String uri;
    // 请求头
    private Map<String, String> header = new HashMap<>(16);
    // 请求体
    private String body;
    // 参数
    private Map<String, String> parameter = new HashMap<>(8);

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    /*
     * 该方法做过修改， 自动生成的参数为以下
     * public Map<String, String> getHeader() {return header}
     */
    public String getHeader(String key) {
        return this.header.get(key);
    }

    /*
     * 该方法做过修改， 自动生成的参数为以下， HttpResponse同理
     * public void setHeader(Map<String, String>header){this.header = header;}
     */
    public void setHeader(String key, String value) {
        this.header.put(key, value);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getParameter(String name) {
        return parameter.get(name);
    }

    public void setParameter(String name, String value) {
        this.parameter.put(name, value);
    }

    @Override
    public String toString() {
        return "HttpRequest [protocol=" + protocol + ", method=" + method + ", uri=" + uri + ", header=" + header
                + ", body=" + body + ", parameter=" + parameter + "]";
    }

}
