package Order_MaxPay;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

//用来分区
public class ItemIdPartitoner extends Partitioner<OrderBean, NullWritable> {

    public int getPartition(OrderBean orderBean, NullWritable nullWritable, int i) {
        //两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。
        //maxvalue : 1111111111111111111111111111111   与去他int值 &(1 对1 为 1  其他为0) 不变
        return (orderBean.getItemid().hashCode() & Integer.MAX_VALUE) % i;
    }
}
