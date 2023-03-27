package test

import java.io

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object Spark11_RDD_10 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD

    //自定义分区器
    val listRDD = sc.makeRDD(List(("a",1),("b",2),("c",3)))

    val partitionRDD: RDD[(String, Int)] = listRDD.partitionBy(new MyParitioner(3))

    partitionRDD.saveAsTextFile("ouput")

  }


}
//声明分区器

class MyParitioner (partitions: Int) extends Partitioner{
  override def numPartitions: Int = {
    partitions
  }

  override def getPartition(key: Any): Int = {
    1
  }
}

