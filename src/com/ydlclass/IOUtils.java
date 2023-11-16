package com.ydlclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class IOUtils {

    // 可返回Optional<String>
    public static String getContext(String path) {
        // 读取某个文件的内容
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        try (
                FileInputStream fileInputStream = new FileInputStream(path);) {
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
