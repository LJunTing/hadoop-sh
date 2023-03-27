package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_RDD {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5))
    listRDD.collect().foreach(println);
    //内存中创建parallelize
    val paraRDD: RDD[Int] = sc.parallelize(Array(1,2,3,4))


    //外部存储中  获取RDD
    //默认读取文件数据类型都是字符串类型
    val line: RDD[String] = sc.textFile("in")

    line.saveAsTextFile("ouput")
  }


}
