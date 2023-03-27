package socket_reflect;

import utils.CloseUtil;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;

public class RunnableServer implements Runnable {
    Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public RunnableServer(Socket serverSocket) {
        this.socket = serverSocket;
    }

    public void run() {

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();

            System.out.println(line);

            if (line.startsWith("9999")) {
                String[] split = line.split(":");
                if (split.length > 0) {
                    String classname = split[1];
                    String method = split[2];

                    Class<?> aClass = Class.forName(classname);
                    Object o = aClass.newInstance();
                    Method method1 = aClass.getMethod(method);
                    Object invoke = method1.invoke(o);


                    PrintWriter printWriter = new PrintWriter(outputStream);
                    printWriter.println("结果:  " + invoke.toString());
                    printWriter.flush();
                    System.out.println("服务端根据全包名获得的方法返回值: " + invoke.toString());

                }
//                for (int i = 0; i < split.length; i++) {
//
//                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeAll(inputStream, outputStream, socket);
        }


    }
}
