package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark07_RDD_06 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 4)
    //glom 算子    讲一个分区的数据  放到一个数组中
    val result: RDD[Array[Int]] = listRDD.glom()
    result.collect().foreach(println)
    //grouvy 一个分区    按照规则分到一个分区
    val groubyRDD: RDD[(Int, Iterable[Int])] = listRDD.groupBy(i => i % 2)
    groubyRDD.collect().foreach(println)

    // //filter 一个分区    按照规则过滤   %2 == 0 的数据留下
    val filterRDD: RDD[Int] = listRDD.filter(_ % 2 == 0)
    filterRDD.collect().foreach(println)

  }


}
