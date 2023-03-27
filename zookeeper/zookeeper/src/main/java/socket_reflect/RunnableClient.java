package socket_reflect;

import utils.CloseUtil;

import java.io.*;
import java.net.Socket;

public class RunnableClient implements Runnable {
    Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public RunnableClient(Socket serverSocket) {
        this.socket = serverSocket;
    }

    public void run() {

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();


            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println("9999:socket_reflect.TestReflect:getData");
            printWriter.flush();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            System.out.println("服务端发来消息: " + line);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.closeAll(inputStream, outputStream, socket);
        }


    }
}
