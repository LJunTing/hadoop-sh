package socket_reflect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServerReflect {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost", 8822));
        while (true) {
            //接受客户端的连接请求
            Socket accept = serverSocket.accept();
            new Thread(new RunnableServer(accept)).start();

        }

    }
}
