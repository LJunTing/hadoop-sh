package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark02_RDD_01 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val ten_num: RDD[Int] = sc.makeRDD(1 to 10)
    //mapPartitions 可以对一个RDD中所有的分区进行遍历
    //    效率优于map算子  减少发送到执行器执行交互次数
    //可能会出现内存溢出  oom
    val mapPartitions: RDD[Int] = ten_num.mapPartitions(datas => {

      datas.map(_ * 2)

    })




    mapPartitions.collect().foreach(println)
  }


}
