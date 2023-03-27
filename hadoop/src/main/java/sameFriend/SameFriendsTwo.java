package sameFriend;

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
import java.util.Arrays;

public class SameFriendsTwo {
    static class SameFriendsTwoMapper extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] own_friends = line.split("\t");
            String own = own_friends[0];
            String friends = own_friends[1];

            String[] friend = friends.split(",");

            Arrays.sort(friend);

            //减去 最后一个不用组  还有自己
            for (int i = 0; i < friend.length - 1 - 1; i++) {
                String friend_1 = friend[i];
                System.out.println("名称friend_1===" + friend_1);
                for (int i1 = i + 1; i1 < friend.length ; i1++) {
                    String friend_2 = friend[i1];
                    System.out.println("名称friend_2====" + friend_2);
                    context.write(new Text(friend_1 + "-" + friend_2), new Text(own));
                }
            }

        }
    }

    static class SameFriendsTwoReduce extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {


            StringBuffer stringBuffer = new StringBuffer();
            for (Text v : values) {
                stringBuffer.append(v).append(" ");
            }
            context.write(key, new Text(stringBuffer.toString()));

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        JobConf conf = new JobConf();
        Job job = Job.getInstance(conf);

        job.setJarByClass(SameFriendsTwo.class);

        job.setMapperClass(SameFriendsTwoMapper.class);
        job.setReducerClass(SameFriendsTwoReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


        FileInputFormat.setInputPaths(job, new Path("c:/lib/friends/input-2"));
        FileOutputFormat.setOutputPath(job, new Path("c:/lib/friends/output-2"));

        boolean b = job.waitForCompletion(true);
        System.out.println("是否成功=" + b);
    }
}
