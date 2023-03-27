package utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {

    //    //关闭工具  java
    public static void closeAll(Closeable... closeables) {
        for (Closeable able : closeables) {
            if (able != null) {
                try {
                    able.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
