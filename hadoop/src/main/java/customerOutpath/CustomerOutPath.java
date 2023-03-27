package customerOutpath;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import javax.xml.soap.Text;
import java.io.IOException;

public class CustomerOutPath extends FileOutputFormat<Text, NullWritable> {
    public RecordWriter getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        FileSystem fileSystem = FileSystem.get(taskAttemptContext.getConfiguration());
        Path enhancedlog = new Path("hdfs://mini1:9000/logenhance/enhancedlog/log.dat");
        Path tocrawl = new Path("hdfs://mini1:9000/logenhance/tocrawl/url.dat");

        FSDataOutputStream enhanceFs = fileSystem.create(enhancedlog);
        FSDataOutputStream tocrawlFs = fileSystem.create(tocrawl);
        //创建不同路径的流  到EnhanceRecordWriter中分发到不同流
        //达到对应路径输出对应内容
        return new EnhanceRecordWriter(enhanceFs, tocrawlFs);
    }

    static class EnhanceRecordWriter extends RecordWriter<Text, NullWritable> {
        FSDataOutputStream enhanceFs = null;
        FSDataOutputStream tocrawlFs = null;

        public EnhanceRecordWriter(FSDataOutputStream enhanceFs, FSDataOutputStream tocrawlFs) {
            this.enhanceFs = enhanceFs;
            this.tocrawlFs = tocrawlFs;
        }

        public void write(Text text, NullWritable nullWritable) throws IOException, InterruptedException {
            String result = text.toString();
            if (result.contains("tocrawl")) {
                tocrawlFs.write(result.getBytes());
            } else if (result.contains("enhance")) {
                enhanceFs.write(result.getBytes());
            }
        }

        public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
            if (tocrawlFs != null) {
                tocrawlFs.close();
            }
            if (enhanceFs != null) {
                enhanceFs.close();
            }
        }
    }


}
