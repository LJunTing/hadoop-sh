package sameFriend;

import InverIndex.InverIndexStepOne;
import com.sun.tools.javac.comp.Lower;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class SameFriendsOne {
    static class SameFriendsOneMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split = line.split(":");
            String own = split[0];
            String own_friends = split[1];

            String[] friends = own_friends.split(",");

            for (String friend : friends) {
                context.write(new Text(friend), new Text(own));
            }
        }
    }

    static class SameFriendsOnReduce extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            StringBuffer stringBuffer = new StringBuffer();
            for (Text value : values) {
                stringBuffer.append(value).append(",");
            }
            context.write(key, new Text(stringBuffer.toString()));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        JobConf conf = new JobConf();
        Job job = Job.getInstance(conf);

        job.setJarByClass(SameFriendsOne.class);

        job.setMapperClass(SameFriendsOneMapper.class);
        job.setReducerClass(SameFriendsOnReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.setInputPaths(job, new Path("c:/lib/friends/input"));
        FileOutputFormat.setOutputPath(job, new Path("c:/lib/friends/output"));

        boolean b = job.waitForCompletion(true);
        System.out.println("是否成功=" + b);
    }
}
