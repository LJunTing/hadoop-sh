package flowsum;

import com.sun.media.sound.SoftTuning;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

public class FlowCout {

    static class FlowCountMapper extends Mapper<Object, Text, Text, FlowBean> {

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String s = value.toString();
            String[] split = s.split("\t");
            System.out.println("length" + split.length);
            String phoneNum = split[1];

            System.out.println("upflow=" + split[split.length - 3]);
            System.out.println("dflow=" + split[split.length - 2]);
            long upflow = 0;
            long dflow = 0;
            try {
                upflow = Long.parseLong(split[split.length - 3].trim());
                dflow = Long.parseLong(split[split.length - 2].trim());
            } catch (Exception e) {
                e.printStackTrace();
            }

            context.write(new Text(phoneNum), new FlowBean(upflow, dflow));
        }
    }

    static class FlowCountReduce extends Reducer<Text, FlowBean, Text, FlowBean> {
        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

            long sum_upflow = 0;
            long sum_dflow = 0;

            for (FlowBean flowBean : values) {
                long sumFlow = flowBean.getSumFlow();
                sum_upflow += flowBean.getUpFlow();
                sum_dflow += flowBean.getdFlow();
            }

            context.write(key, new FlowBean(sum_upflow, sum_dflow));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        JobConf conf = new JobConf();

        conf.set("mapreduce.framework.name", "local");
//        entries.set("fs.defaultFS", "file:///");
        conf.set("fs.defaultFS", "hdfs://mini1:9000");
        Job job = Job.getInstance(conf);

        job.setJarByClass(FlowCout.class);

        job.setMapperClass(FlowCountMapper.class);
        job.setReducerClass(FlowCountReduce.class);
        //分区
//        job.setPartitionerClass(ProvincePartitioner.class);
        //指定reduce  task几个区
//        job.setNumReduceTasks(5);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);


        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);
        System.out.println("是否成功=" + b);

    }

}
