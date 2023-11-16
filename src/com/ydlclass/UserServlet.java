package com.ydlclass;

public class UserServlet implements Servlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        int id = Integer.parseInt(request.getParameter("id")); // 拿到id具体的值
        UserDao userDao = new UserDao();
        User user = userDao.findUserById(id);
        //
        if (user != null) {

            response.setHeader("Content-Type", "text/plain;charset=UTF-8");
            response.setHeader("Content-Length",
                    Integer.toString(user.toString().getBytes().length));
            response.setBody(user.toString());

            // 将响应报文写出给浏览器
            HttpResponseHandler.write(response.getOutputStream(), response);
        }
    }

}
