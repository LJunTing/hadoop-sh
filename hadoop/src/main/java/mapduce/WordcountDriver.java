package mapduce;

import com.sun.istack.NotNull;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.StringUtils;

import javax.security.auth.login.AppConfigurationEntry;
import java.io.IOException;

/**
 * 相当于一个yarn 集群的客户端
 * 需要子在此封装我们的mr程序相关运行参数 指定jar包
 * 最后提交给yarn
 */
public class WordcountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length == 2) {
            System.err.println("输出路径:" + args[1]);

        }
        JobConf entries = new JobConf();

//        entries.set("mapreduce.framework.name", "yarn");
//        entries.set("yarn.resoucemanager.hostname", "mini1");

        Job job = Job.getInstance(entries);

        //指定本程序的jar包所在地
        job.setJarByClass(WordcountDriver.class);

        //指定本业务job要使用的mapper业务类
        job.setMapperClass(WordcountMapper.class);
        job.setReducerClass(WordcountReduce.class);
        //mapper的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //指定最终输出数据的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //指定job的输入原始所在的目录

        //导包注意  耽误好多时间( org.apache.hadoop.mapreduce.lib.FileInputFormat)
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        //指定输出结果目录
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

//        System.err.println("获取输出路径:" + entries.get("mapreduce.output.fileoutputformat.outputdir"));
        //将job中的配置相关参数,以及job所用的java类用的jar包,提交给yarn去运行
//        job.submit();
        //true 将信息打印出来
        boolean b = job.waitForCompletion(true);

        System.out.println("是否成功: " + b);
    }
}
