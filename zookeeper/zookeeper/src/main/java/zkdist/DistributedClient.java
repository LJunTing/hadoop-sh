package zkdist;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributedClient {
    private static String connectstring = "mini1:2181,mini2:2181,mini3:2181";
    private static int timeout = 2000;
    private ZooKeeper zooKeeper;
    private String parentNote = "/servers";
    //注意加volatile意义
    private volatile ArrayList<String> serverlist;

    public void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectstring, timeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + watchedEvent.getPath());
                try {
                    //重新更新服务器列表,并且注册了监听
                    getServerList();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void getServerList() throws KeeperException, InterruptedException {
        //获取服务器子节点信息  并对父节点进行监听
        List<String> children = zooKeeper.getChildren(parentNote, true);
        ArrayList servers = new ArrayList<String>();
        for (String child : children) {
            //parentNote父节点  child只是子节点名字
            byte[] data = zooKeeper.getData(parentNote + "/" + child, false, null);
            boolean add = servers.add(new String(data));
        }
        //把servers赋值给成员变量serverlist,已提供给各业务线程使用
        serverlist = servers;
        System.out.println(serverlist.toString());
    }

    public void handleBussiness() throws InterruptedException {
        System.out.println( "client start working....");
        Thread.sleep(Long.MAX_VALUE);
    }


    public static void main(String[] args) throws Exception {
        //链接客户端
        DistributedClient distributedClient = new DistributedClient();
        distributedClient.getConnect();
        //获取服务列表
        distributedClient.getServerList();
        //业务功能启动
        distributedClient.handleBussiness();

    }
}
