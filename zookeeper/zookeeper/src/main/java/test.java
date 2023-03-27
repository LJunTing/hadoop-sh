import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class test {
    private static String connectstring = "mini1:2181,mini2:2181,mini3:2181";
    private static int timeout = 2000;
    private static Watcher watch;
    private static ZooKeeper zkCLient;

    @Before
    public void init() {
        try {
            zkCLient = new ZooKeeper(connectstring, timeout, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    System.out.println(watchedEvent.getType() + watchedEvent.getPath());
                    try {
                        zkCLient.getChildren("/", true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
//            setzookeeper(zkCLient);
//            getzookeeper(zkCLient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //数据的增删改查
    public static void setzookeeper(ZooKeeper zkCLient) throws KeeperException, InterruptedException {
        String s = zkCLient.create("/idea", "nihao".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public  void getzookeeper() throws KeeperException, InterruptedException {
        List<String> children = zkCLient.getChildren("/", true);

        for (String child : children) {
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);

    }
}
