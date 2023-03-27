package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocketserver {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress("localhost", 8892));
        //接受客户端的链接请求 ; accept 是一个阻塞的方法 会一直等待 直到有客户端请求
        while (true) {
            Socket accept = socket.accept();
            new Thread(new ServiceserverTask(accept)).start();
        }
    }
}
