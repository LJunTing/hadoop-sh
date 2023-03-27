package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object test {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    val sc = new SparkContext(config)

    val listRDD: RDD[(String, Int)] = sc.makeRDD(List(("a", 3), ("a", 2) ,("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)

    /**
      * #  aggregateByKey， 先说分为三个参数的：
      * #第一个参数是， 每个key的初始值
      * #第二个是个函数， Seq Function， 经测试这个函数就是用来先对每个分区内的数据按照key分别进行定义进行函数定义的操作
      * 分区一
      * 0-比-3-比-2-->3
      * 0-比-4-->4
      * 分区二
      * 0-比-3-->3
      * 0-比-6-比-8-->8
      * #第三个是个函数， Combiner Function， 对经过 Seq Function 处理过的数据按照key分别进行进行函数定义的操作
      */
    //listRDD  glomRDD
    val aRDD: RDD[(String, Int)] = listRDD.aggregateByKey(0)(math.max(_, _), _ -_)


    aRDD.collect().foreach(println)
  }

}
