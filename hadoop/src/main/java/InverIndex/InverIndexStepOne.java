package InverIndex;

import flowsum.FlowBean;
import flowsum.FlowCout;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class InverIndexStepOne {

    static class InverIndexStepOneMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        Text k = new Text();
        //默认计数1
        IntWritable v = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split(" ");
            FileSplit inputSplit = (FileSplit) context.getInputSplit();
            String name = inputSplit.getPath().getName();
            for (String word : words) {
                //单词和 单词所在文件的路径作为key
                k.set(word + "--" + name);
                context.write(k, v);
            }

        }
    }

    static class InverIndexStepOneReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            for (IntWritable v : values) {
                count += v.get();
            }

            context.write(key, new IntWritable(count));

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        JobConf conf = new JobConf();

//        conf.set("mapreduce.framework.name", "local");
////        entries.set("fs.defaultFS", "file:///");
//        conf.set("fs.defaultFS", "hdfs://mini1:9000");
        Job job = Job.getInstance(conf);

        job.setJarByClass(InverIndexStepOne.class);

        job.setMapperClass(InverIndexStepOneMapper.class);
        job.setReducerClass(InverIndexStepOneReduce.class);
        //分区
//        job.setPartitionerClass(ProvincePartitioner.class);
        //指定reduce  task几个区
//        job.setNumReduceTasks(5);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        FileInputFormat.setInputPaths(job, new Path("c:/lib/wordcount/input"));
        FileOutputFormat.setOutputPath(job, new Path("c:/lib/wordcount/output"));

        boolean b = job.waitForCompletion(true);
        System.out.println("是否成功=" + b);
    }


}
