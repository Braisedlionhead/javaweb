package com.ydlclass.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import com.ydlclass.core.Configuration;

public class JDBCUtil {
    public static Connection getConnection() {
        // 1.数据库连接的4个基本要素：

        // old
        // InputStream in =
        // JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
        // Properties properties = new Properties();
        // try {
        // properties.load(in);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // String url = properties.getProperty("url");
        // String user = properties.getProperty("username");
        // String password = properties.getProperty("password");

        // new
        Map<String, String> dataSource = Configuration.getDataSource();

        String url = dataSource.get("url");
        String user = dataSource.get("username");
        String password = dataSource.get("password");

        // 2.获取连接
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
