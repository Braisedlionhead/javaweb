package com.ydlclass.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 封装一个响应对象
 */
public class HttpResponseHandler implements Serializable {

    public static String build(HttpResponse httpResponse) {
        StringBuilder response = new StringBuilder();
        response.append(httpResponse.getProtocol()).append(" ")
                .append(httpResponse.getCode()).append(" ")
                .append(httpResponse.getMessage()).append("\r\n");
        // 拼接首部信息
        for (Map.Entry<String, String> entry : httpResponse.getHeaders().entrySet()) {
            response.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");

        }
        response.append("\r\n"); // 最后一行必须有两个\r\n
        if (httpResponse.getBody() != null) {
            response.append(httpResponse.getBody());
        }
        return response.toString();
    }

    public static void write(OutputStream outputStream, HttpResponse httpResponse) {
        String message = HttpResponseHandler.build(httpResponse);
        try {
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode("302");
        httpResponse.setMessage("Moved Temporarily");
        httpResponse.setHeader("Location", "https://www.google.com");
        System.out.println(HttpResponseHandler.build(httpResponse));
    }
}
