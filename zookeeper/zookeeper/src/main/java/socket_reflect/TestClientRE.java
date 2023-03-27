package socket_reflect;

import java.io.IOException;
import java.net.Socket;

public class TestClientRE {
    public static void main(String[] args) throws IOException {
        //需要链接的服务器地址 端口
        Socket socket = new Socket("localhost", 8822);

        new Thread(new RunnableClient(socket)).start();
    }
}
