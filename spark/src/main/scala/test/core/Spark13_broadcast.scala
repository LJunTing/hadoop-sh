package test.core

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 使用广播器
  *
  * 用于优化调优
  */

object Spark13_broadcast {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    val listRDD = sc.makeRDD(List((1, "a"), (2, "b"), (3, "c")))
    val list = List((1, 1), (2, 2), (3, 3))
    //可以使用广播减少数据的传输
    val broadcast: Broadcast[List[(Int, Int)]] = sc.broadcast(list)
    val result: RDD[(Int, (String, Any))] = listRDD.map(data => {
      var v2: Any = null
      for (t <- broadcast.value) {
        if (data._1 == t._1) {
          v2 = t._2
        }
      }
      (data._1, (data._2, v2))
    })
    result.foreach(println)
  }


}
