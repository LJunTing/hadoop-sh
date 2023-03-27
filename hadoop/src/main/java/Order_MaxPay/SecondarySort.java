package Order_MaxPay;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import sameFriend.SameFriendsOne;

import java.io.IOException;

public class SecondarySort {
    static class SecondarySortMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {
        OrderBean orderBean = new OrderBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split(",");
            orderBean.setAmuount(new DoubleWritable(Double.parseDouble(fields[2])));
            orderBean.setItemid(new Text(fields[0]));
            context.write(orderBean, NullWritable.get());

        }
    }

    static class SecondarySortReduce extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable> {

        @Override
        protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        JobConf conf = new JobConf();
        Job job = Job.getInstance(conf);

        job.setJarByClass(SecondarySort.class);

        job.setMapperClass(SecondarySortMapper.class);
        job.setReducerClass(SecondarySortReduce.class);

        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);


        FileInputFormat.setInputPaths(job, new Path("c:/lib/sqlordersort/input"));
        FileOutputFormat.setOutputPath(job, new Path("c:/lib/sqlordersort/output"));

        job.setGroupingComparatorClass(ItemidGroupingComparator.class);
        job.setPartitionerClass(ItemIdPartitoner.class);
        job.setNumReduceTasks(1);
        boolean b = job.waitForCompletion(true);
        System.out.println("是否成功=" + b);
    }
}
