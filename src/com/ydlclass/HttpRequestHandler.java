package com.ydlclass;

import java.io.IOException;
import java.io.InputStream;

/**
 * 处理请求
 */
public class HttpRequestHandler {

    // 原本传入的参数是字符串 （即请求的报文）
    // 后改为一个输入流， 为了让主程序方便
    public static HttpRequest getRequest(InputStream inputStream) {
        // 从流中读取请求的报文
        StringBuilder request = new StringBuilder(); // 涉及到拼接字符串的内容可以用StringBuilder， 需要用到时可以调用其toString方法

        try {
            byte[] buf = new byte[1024];
            int len;

            // 连接刚建立， 浏览器还没有发送请求就读取输入流了， 会出问题， 可以让线程睡1000ms，
            // 但是用do-while更合适，因为InputStream.read方法是阻塞的
            /*
             * old
             * while (inputStream.available() > 0) {
             * len = inputStream.read(buf);
             * request.append(new String(buf, 0, len));
             * }
             */

            // new
            do {
                len = inputStream.read(buf);
                request.append(new String(buf, 0, len));
            } while (inputStream.available() > 0);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(request);

        // GET / 127.0.0.1/ ______起始行 或 请求行？
        // HOST: 127.0.0.1:8888 ______首部信息

        // a=b&c=d
        HttpRequest httpRequest = new HttpRequest();
        String[] headAndBody = request.toString().split("\r\n\r\n");
        // 先处理实体
        if (headAndBody.length > 1 && headAndBody[1] != null) {
            httpRequest.setBody(headAndBody[1]);
        }
        // 处理请求行
        String startAndHeader = headAndBody[0]; // 起始行和首部信息
        String[] startAndHeaders = startAndHeader.split("\r\n");
        //
        // 此处就不做非空判断了 （真实情况必要！）， 因为视频中的项目一定存在 （即请求行不为空）
        // 请求行
        String startLine = startAndHeaders[0];
        String[] startLineElement = startLine.split(" "); // 拿到请求行的每一个元素
        httpRequest.setMethod(startLineElement[0]);
        // 处理uri， 可能有参数 /user?name=zs&id=1 ，就需要判断?后是否有参数
        String[] uriAndParam = startLineElement[1].split("\\?");// 在regex中 ? 有特殊含义， 需要"?"就要转义，但是regex中两个\表示"\",//
                                                                // 故出现了此处的"\\?"
        httpRequest.setUri(uriAndParam[0]);
        if (uriAndParam.length > 1 && uriAndParam[1] != null) {
            String[] params = uriAndParam[1].split("&");
            for (String s : params) {
                String[] param = s.split("=");
                httpRequest.setParameter(param[0], param[1]);
            }
        }
        // httpRequest.setUri(startLineElement[1]);
        httpRequest.setProtocol(startLineElement[2]);

        // 处理首部信息，startAndHeaders中除了第一行外， 从第二行起， 都是首部信息
        for (int i = 1; i < startAndHeaders.length; i++) {
            String header = startAndHeaders[i];
            String[] keyAndValue = header.split(": "); // 得到key和value
            httpRequest.setHeader(keyAndValue[0], keyAndValue[1]);
        }
        return httpRequest;
    }
}
