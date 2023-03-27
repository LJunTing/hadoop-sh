package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
object Spark08_RDD_07 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD
    val listRDD: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 1))
    //

    //c从指定的数据集合中进行抽样处理,根据不同的算法进行抽样
    /**
      *   withReplacement:  true  放回抽样  false 不放回抽样
      * fraction: 没个数据的概率
      * seed: 种子   不写默认以时间为种子
      */
    val sampleRDD: RDD[Int] = listRDD.sample(false,0.4,1)
    sampleRDD.collect().foreach(println)


  }


}
