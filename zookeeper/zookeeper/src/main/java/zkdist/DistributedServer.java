package zkdist;

import org.apache.zookeeper.*;

import java.io.IOException;

//zookeeper  操作服务端
public class DistributedServer {
    private static String connectstring = "mini1:2181,mini2:2181,mini3:2181";
    private static int timeout = 2000;
    private ZooKeeper zooKeeper;
    private String parentNote = "/servers";

    public void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectstring, timeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + watchedEvent.getPath());
                try {
                    zooKeeper.getChildren("/", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    //向zookeeper集群注册服务器信息  服务器地址+序列号
    public void registerServer(String hostname) throws KeeperException, InterruptedException {
        String creatPath = zooKeeper.create(parentNote + "/server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(hostname + "is online" + creatPath);
    }

    public void handleBussiness(String hostname) throws InterruptedException {
        System.out.println(hostname + "start working....");
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {

        String hostname = args[0];
        //获取zk链接
        DistributedServer distributedServer = new DistributedServer();
        distributedServer.getConnect();
        //利用zk链接注册服务器信息
        distributedServer.registerServer(hostname);
        //启动业务功能
        distributedServer.handleBussiness(hostname);

    }

}
