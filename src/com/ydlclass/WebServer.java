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
            // 获得输入流和输出流
            InputStream inputStream = accept.getInputStream();
            OutputStream outputStream = accept.getOutputStream();
            // 获取请求， 封装响应
            HttpRequest request = HttpRequestHandler.getRequest(inputStream);
            HttpResponse response = new HttpResponse();
            String uri = request.getUri();
            // 静态资源
            if (uri.contains(".html")) {
                // 如果是静态资源， 要拿到静态资源的具体路径
                String path = Constant.ROOT_DIR + request.getUri();
                String body = IOUtils.getContext(path);
                if (body != null) {

                    // String body = "<h1 style=\"color:red\"><del>hello server!?</del><h1>";
                    response.setHeader("Content-Type", "text/html;charset=UTF-8");
                    response.setHeader("Content-Length",
                            Integer.toString(body.getBytes().length));
                    response.setBody(body);

                    // 将响应报文写出给浏览器
                    HttpResponseHandler.write(outputStream, response);
                    // outputStream.write(response2.getBytes());
                }
                // 处理动态资源
            } else {

            }

            // 获取资源所在的文件地址
            // request.getUri(); 得到的可以是 index.html

            // 给浏览器响应

            // 处理数据库 （访问数据库）
            response.setOutputStream(outputStream); // 将流设置到response中， 因为将来要用流给浏览器中写东西
            //
            Servlet servlet = Container.getServlet(request.getUri());
            // 避免空指针异常
            if (servlet != null) {
                servlet.service(request, response);
            }

            outputStream.close();
            inputStream.close();
            accept.close();
        }
    }
}
