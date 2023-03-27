package socket;

import utils.CloseUtil;

import java.io.*;
import java.net.Socket;

public class ServiceserverTask implements Runnable {
    Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ServiceserverTask(Socket socket) {
        this.socket = socket;
    }

    public void run() {

//        Scanner scanner = new Scanner(System.in);

//        while (true) {
            try {
                //从socket 链接中获取到与client之间的网络通信输入流
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = bufferedReader.readLine();
//                System.out.println("来自客户端的消息==" + line+" \n 请您输入:");

                System.out.println("[服务端] 来自客户端的消息: " + line);
//                String next = scanner.nextLine();
                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.println("ok--接到消息: "+line);
                printWriter.flush();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //关闭工具类
                CloseUtil.closeAll(inputStream, outputStream);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
    }
}
