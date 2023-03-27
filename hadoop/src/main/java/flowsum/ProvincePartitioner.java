package flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * 分开手机号归属地
 * k2 v2 对应的是map输出的kv类型
 */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {

    private static HashMap<String, Integer> integerHashMap = new HashMap<String, Integer>();

    static {
        integerHashMap.put("136", 0);
        integerHashMap.put("137", 1);
        integerHashMap.put("138", 2);
        integerHashMap.put("139", 3);
    }


    //分区器
    public int getPartition(Text text, FlowBean flowBean, int i) {
        String substring = text.toString().substring(0, 3);
        Integer integer = integerHashMap.get(substring);

        return integer == null ? 4 : integer;
    }
}
