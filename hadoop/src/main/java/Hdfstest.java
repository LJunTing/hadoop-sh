import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

public class Hdfstest {


    private FileSystem fileSystem;
    private Configuration conf;

    @Before
    public void init() {
        conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://mini1:9000");
        try {
            fileSystem = FileSystem.get(new URI("hdfs://mini1:9000"), conf, "root");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upload() throws IOException {
        fileSystem.copyFromLocalFile(new Path("C:/aow_drv.log"), new Path("/"));
        fileSystem.close();
    }

    @Test
    public void download() throws IOException {
        fileSystem.copyToLocalFile(new Path("/aow_drv.log"), new Path("C:/lib/"));
        fileSystem.close();
    }

    @Test
    public void seeConf() {
        //查看配置
        Iterator<Map.Entry<String, String>> iterator = conf.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println("配置" + next.getKey() + "  -  " + next.getValue());
        }

    }

    /*
       通过流的方式 上传 文件到hdfs
     */
    @Test
    public void testupload() throws IOException {
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/testupload"));
        FileInputStream fileInputStream = new FileInputStream("c:/testupload");
        IOUtils.copy(fileInputStream, fsDataOutputStream);

    }

    /*
       通过流的方式 下载 文件到hdfs
     */
    @Test
    public void testdownload() throws IOException {
        FSDataInputStream open = fileSystem.open(new Path("/testupload"));
        FileOutputStream fileOutputStream = new FileOutputStream("c:/testdownload");

        IOUtils.copy(open, fileOutputStream);
    }

    /**
     * 读写指定范围的内容
     *
     * @throws IOException
     */
    @Test
    public void testRandoomAccess() throws IOException {
        FSDataInputStream open = fileSystem.open(new Path("/testupload"));
        FileOutputStream fileOutputStream = new FileOutputStream("d:/testupload");
        open.seek(12);
        IOUtils.copy(open, fileOutputStream);

    }

    /**
     * 显示文件内容到 屏幕
     */
    public void readtext() throws IOException {
        FSDataInputStream open = fileSystem.open(new Path("/testupload"));
        IOUtils.copy(open, System.out);
    }

    /**
     * 显示文件列表
     * 文件 block 信息查看
     */
    public void filelist() throws IOException {
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path("/"), true);

        while (iterator.hasNext()) {
            LocatedFileStatus next = iterator.next();
            System.out.println("file name=" + next.getPath().getName());

            BlockLocation[] blockLocations = next.getBlockLocations();
            for (BlockLocation block : blockLocations) {
                System.out.println("块 的大小: " + block.getLength());
                System.out.println("块的起始偏移量: " + block.getOffset());
                System.out.println("块的起始偏移量: " + block.getOffset());
                System.out.println("块的主机(datanode节点): " + block.getHosts());//可能有多台 数组

                System.out.println("---------------------------------");
            }
        }
    }

    @Test
    public void test_split() {
        /**
         * i-k i-c i-b i-g
         */
        String line = "i,k,c,b,g,";
        String[] split = line.split(",");

        //两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。
        System.out.println("maxValue=  ================================" );
        int maxValue = Integer.MAX_VALUE;
        System.out.println("maxValue=  " + maxValue);
        int i = (123142324 & Integer.MAX_VALUE);

        System.out.println("i=  " + i);

    }
}
