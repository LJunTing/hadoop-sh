package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark05_RDD_04 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val unit: RDD[List[Int]] = sc.makeRDD(Array(List(1, 2), List(7, 8)))

    val result: RDD[Int] = unit.flatMap(datas => datas)

    result.collect().foreach(println)

  }


}
