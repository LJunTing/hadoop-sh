package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val ten_num: RDD[Int] = sc.makeRDD(1 to 10)
    //map算子
    val mapRDD: RDD[Int] = ten_num.map(_ * 2)


    mapRDD.collect().foreach(println)
  }


}
