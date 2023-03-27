package socket;

import utils.CloseUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Clientrunnable implements Runnable {
    Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

//    public Clientrunnable(Socket socket) {
//        this.socket = socket;
//    }

    public void run() {
        try {

            Scanner scanner = new Scanner(System.in);
            long l = System.currentTimeMillis();
            while (true) {
                socket = new Socket("localhost", 8892);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                System.out.println("请输入信息:");
                String content = scanner.nextLine();
                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.println(content);
                printWriter.flush();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = bufferedReader.readLine();
                System.out.println("[客户端] 来自服务端的消息: " + line);

                CloseUtil.closeAll(inputStream, outputStream);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
}
