//package test
//
//import org.apache.spark.rdd.RDD
//import org.apache.spark.{SparkConf, SparkContext}
//
////日志分割   算子实战
//object test_ad_touch {
//
//  def main(args: Array[String]): Unit = {
//    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
//    val sc = new SparkContext(config)
//
//    val source: RDD[Array[String]] = sc.textFile("in").map(_.split(" "))
//    val groups: RDD[(Any, Any, Any)] = source.map(sourc => (source(0), source(1), source(2)))
//
////    groups.groupBy(item => (item._2)).map(subG => {
////
////
////      })
//
//  }
//
//
//}
