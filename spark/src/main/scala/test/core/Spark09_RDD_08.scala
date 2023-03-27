package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark09_RDD_08 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 1, 2, 5, 6, 9, 1))
    // distinct
    /**
      * 去除重复数据   然后打乱重组  到不同分区
      *
      * 数据减少   可以改变默认的分区数量
      * numPartitions  N 重新把数据存到  N个分区中
      *
      * 也可以不写    按照原来的分区数
      */
    val distinctRDD: RDD[Int] = listRDD.distinct(2)


    distinctRDD.collect().foreach(println)


  }


}
