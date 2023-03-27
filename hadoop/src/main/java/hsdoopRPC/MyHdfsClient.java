package hsdoopRPC;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHdfsClient {
    public static void main(String[] args) throws IOException {
        CLientNamenodeProtocal namenode = RPC.getProxy(CLientNamenodeProtocal.class, 1L, new InetSocketAddress("localhost", 8888), new Configuration());
        String metaData = namenode.getMetaData("/");
        System.out.println("结果: " + metaData);
    }
}
