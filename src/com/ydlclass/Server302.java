package com.ydlclass;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server302 {

    public static void main(String[] args) throws IOException {
        // 创建一个服务器监听在8888端口
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket server = serverSocket.accept();
        OutputStream outputStream = server.getOutputStream();
        // 按照http协议的格式封装一个可以重定向的报文
        String response = "HTTP/1.1 302 Moved Temporarily\r\n" +
                "Location: https://www.google.com\r\n\r\n";

        // 将报文写出给浏览器
        outputStream.write(response.getBytes());
        outputStream.flush();
        // 这个输出流不要着急关，因为突然的关闭会导致浏览器和服务器的连接断开
    }
}
