package mapduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 这些类型根据自己需求来的
 * <p>
 * KEYIN : 默认情况下,是mr框架所独到的一行文本的起始偏移量 long,
 * 在hadoop中有自己的更经济爱你的序列化接口  用--LongWritable
 * VALUEIN:  是mr框架所读到 的一行文本的内容,string   --Text
 * KEYOUT : 是用户自定义逻辑处理完成之后 输出数据中的key,在此处是单词次数 string --Text
 * VALUEOUT:是用户自定义逻辑处理完成之后 输出数据中的 value 此处是单词的次数  integer  --IntWritable
 */
public class WordcountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    /**
     * map阶段的业务逻辑就写在自定义的map()方法中
     * maptask 会对没一行输入数据调用一次我们自定义的map() 方法
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将maptask传给我们的文本内容先转换成String
        String line = value.toString();
        //根据空格将这一行切分成单词
        String[] words = line.split(" ");
        //将单词输出为<单词,1>
        for (String word : words) {
            //将单词作为key , 将本次1作为value ,以便于后续的数据分发,
            //可以根据单词分发,以便于相同单词会到相同reduce task中
            context.write(new Text(word), new IntWritable(1));
        }


    }
}
