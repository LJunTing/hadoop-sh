package utils

import java.io.Closeable
import java.io.IOException

//转换学习kotlin
object CloseUtils {

    //关闭工具
    fun closeAll(vararg closeables: Closeable) {
        for (able in closeables) {
            if (able != null) {
                try {
                    able.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

    }


//    //关闭工具  java
//    public static void closeAll(Closeable... closeables) {
//        for (Closeable able : closeables) {
//            if (able != null) {
//                try {
//                    able.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
}
