package socket;

import java.io.IOException;

public class SocketClient {


    public static void main(String[] args) throws IOException {



        new Thread(new Clientrunnable()).start();


    }
}
