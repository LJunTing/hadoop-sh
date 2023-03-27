package test.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreaming01 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("stream")

    val stream = new StreamingContext(conf, Seconds(3))

    val datas: ReceiverInputDStream[String] = stream.socketTextStream("mini1", 9999)
    val flatmap: DStream[String] = datas.flatMap(_.split(" "))
    val word: DStream[(String, Int)] = flatmap.map((_, 1))
    val result: DStream[(String, Int)] = word.reduceByKey(_ + _)
    result.print()

    stream.start()
    stream.awaitTermination()
  }
}

