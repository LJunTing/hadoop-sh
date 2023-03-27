package mapduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * keyin, keyout 对应mapper 的值类型   keyout, valueout
 * <p>
 * keout : 单词
 * valueout: 总次数
 */
public class WordcountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int count = 0;
        Iterator<IntWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            IntWritable next = iterator.next();
            int i = next.get();
            count += i;
        }
        //数据收集输出u
        context.write(key, new IntWritable(count));

    }
}
