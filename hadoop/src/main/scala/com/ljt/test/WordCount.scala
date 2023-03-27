//package com.ljt.test
//
//import org.apache.spark.rdd.RDD
//import org.apache.spark.{SparkConf, SparkContext}
//
//object WordCount {
//  //使用开发工具完成  spark wordcount的开发
//  def main(args: Array[String]): Unit = {
//    //local模式
//    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
//    //创建spark上下文对象
//    val sc = new SparkContext(config)
//    //读取文件将文件内容  一行行的读出来
//     val line: RDD[String] = sc.textFile("in")
//    //将一行行的数据   分割成一个个单词
//    val words: RDD[String] = line.flatMap(_.split(" "))
//    //为了统计方便,将单词数据进行数据转换
//    val map: RDD[(String, Int)] = words.map((_, 1))
//    //对转换结构后的数据进行分组聚合
//    val wordSum: RDD[(String, Int)] = map.reduceByKey(_ + _)
//    //将统计结果采集后打印到控制台
//    val result: Array[(String, Int)] = wordSum.collect()
//    result.foreach(println);
//  }
//
//}
