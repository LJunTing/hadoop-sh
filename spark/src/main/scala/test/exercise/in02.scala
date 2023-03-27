package test.exercise

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object in02 {


  def main(args: Array[String]): Unit = {
    //    val session: SparkSession = SparkSession.builder().appName("test02").master("local[*]").getOrCreate()


    //    val context: SparkContext = interpreter.session.sparkContext

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("test02")
    val context = new SparkContext(conf)
    //    personNum(context)


    //    tom_avg(context)

    val unit: RDD[( Int)] = context.textFile("in02").map {
      case line => {
        val strings: Array[String] = line.split(",")
        (strings(2).toInt)
      }
    }
    unit.sortBy(x=>x,false)
      .repartition(1)
      .foreach(println)


  }

  //tom平均分
  private def tom_avg(context: SparkContext) = {
    //切分过滤出Tom
    val unit: RDD[(String, Int)] = context.textFile("in02").map {
      case line => {
        val strings: Array[String] = line.split(",")
        (strings(0), strings(2).toInt)
      }
    }.filter(x => x._1.equals("Tom"))
    //科数
    val l: Long = unit.count()
    //聚合 value 筛选出total
    val value: RDD[Int] = unit.reduceByKey(_ + _).map(_._2)
    //算下平均值就行
    value.foreach {
      case x => {
        println(x / l)
      }
    }

    //方法二
//    val unit: RDD[(Int, Int)] = context.textFile("in02").map {
//      case line => {
//        val strings: Array[String] = line.split(",")
//        (strings(0), strings(2).toInt, 1)
//      }
//    }.filter(x => x._1.equals("Tom")).map(map => (map._2, map._3))
//
//    val tuple: (Int, Int) = unit.reduce((x, y) => (x._1 + y._1, x._2 + y._2))
//    println(tuple)
  }

  private def personNum(context: _root_.org.apache.spark.SparkContext) = {
    val unit: RDD[String] = context.textFile("in02").map {
      case line => {
        line.split(",")(0)
      }
    }
    println(unit.distinct().count())
  }
}
