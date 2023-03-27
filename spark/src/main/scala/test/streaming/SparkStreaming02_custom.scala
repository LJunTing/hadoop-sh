package test.streaming

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 自定义数据采集器
  */

object SparkStreaming02_custom {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("stream")

    val stream = new StreamingContext(conf, Seconds(3))

    val receiver = new MyReceiver("mini1", 9999)
    val wordStream: ReceiverInputDStream[String] = stream.receiverStream(receiver)

    val flatmap: DStream[String] = wordStream.flatMap(_.split(" "))
    val word: DStream[(String, Int)] = flatmap.map((_, 1))
    val result: DStream[(String, Int)] = word.reduceByKey(_ + _)
    result.print()

    stream.start()
    stream.awaitTermination()

  }


}


//自定义数据采集器
class MyReceiver(host: String, port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {

  var socket: java.net.Socket = null

  var line: String = null

  def receive(): Unit = {

    socket = new Socket(host, port)

    val reader = new BufferedReader(new InputStreamReader(socket.getInputStream, "UTF-8"))
    while ((line = reader.readLine()) != null) {

      if ("END".equals(line)) {
        return
      } else {
        this.store(line)
      }
    }

  }

  override def onStart(): Unit = {
    new Thread(new Runnable {
      override def run(): Unit = {
        receive()
      }
    }).start()


  }


  override def onStop(): Unit = {
    if (socket != null) {
      socket.close()
      socket = null
    }
  }
}

