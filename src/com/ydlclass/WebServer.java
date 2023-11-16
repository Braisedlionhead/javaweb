package com.ydlclass;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务已经监听在" + port + "端口");
        while (true) {
            Socket accept = serverSocket.accept();
            // 获得输入流
            InputStream inputStream = accept.getInputStream();
            HttpRequest request = HttpRequestHandler.getRequest(inputStream);
            System.out.println(request);

            // 获取资源所在的文件地址
            // request.getUri(); 得到的可以是 index.html
            String path = Constant.ROOT_DIR + request.getUri();

            // 给浏览器响应
            OutputStream outputStream = accept.getOutputStream();

            // 处理数据库 （访问数据库）
            HttpResponse response = new HttpResponse();
            response.setOutputStream(outputStream); // 将流设置到response中， 因为将来要用流给浏览器中写东西
            //
            Servlet servlet = Container.getServlet(request.getUri());
            // 避免空指针异常
            if (servlet != null) {
                servlet.service(request, response);
            }

            //
            // 以下是处理html的
            // 按照http协议的格式封装一个报文
            //
            // HttpResponse response = new HttpResponse();
            // String body = IOUtils.getContext(path);
            // if (body != null) {

            // // String body = "<h1 style=\"color:red\"><del>hello server!?</del><h1>";
            // response.setHeader("Content-Type", "text/html;charset=UTF-8");
            // response.setHeader("Content-Length",
            // Integer.toString(body.getBytes().length)); //
            // 也可以用这种方法body.getBytes().length+""
            // response.setBody(body);
            // String response2 = "HTTP/1.1 200 OK\r\n" +
            // "Content-Length: 39\r\n" +
            // "Content-Type: text/html;charset=UTF-8\r\n\r\n" +
            // "<h1 style=\"color:red\">hello server!<h1>";

            // // 将响应报文写出给浏览器
            // HttpResponseHandler.write(outputStream, response);
            // // outputStream.write(response2.getBytes());
            // }
            //

            outputStream.close();
            inputStream.close();
            accept.close();
        }
    }
}
