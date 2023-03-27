package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark10_RDD_09 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val listRDD: RDD[Int] = sc.makeRDD(1 to 16,4)
    // coalesce
    /**
      * 缩减分区数量:  合并分区
      *
      *
      *
      */
    println("RDD 分区数量:  "+listRDD.partitions.size)

    val coalesceRDD: RDD[Int] = listRDD.coalesce(3)
    println("RDD coalesce后分区数量  :  "+coalesceRDD.partitions.size)

//    coalesceRDD.collect().foreach(println)


  }


}
