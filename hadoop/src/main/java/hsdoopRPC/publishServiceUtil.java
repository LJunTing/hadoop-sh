package hsdoopRPC;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

public class publishServiceUtil {
    public static void main(String[] args) throws IOException {
        RPC.Builder builder = new RPC.Builder(new Configuration());
        builder.setBindAddress("localhost")
                .setPort(8888)
                .setProtocol(CLientNamenodeProtocal.class)
                .setInstance(new Mynamenode());
        RPC.Server server = builder.build();
        server.start();

    }
}
