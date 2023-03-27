package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark03_RDD_03 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val ten_num: RDD[Int] = sc.makeRDD(1 to 10)

    val result: RDD[(Int, String)] = ten_num.mapPartitionsWithIndex {
      case (num, datas) => {
        datas.map((_, ", 分区号:" + num))
      }

    }
    result.collect().foreach(println)


  }


}
