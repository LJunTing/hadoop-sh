package hsdoopRPC;

public class Mynamenode implements CLientNamenodeProtocal {


    /**
     * 模拟namenode的业务方法之一: 查询元数据
     *
     * @param path
     * @return
     */
    public String getMetaData(String path) {
        return path + ": 3 - {BLK_1,BLK_2} ....";
    }

}
