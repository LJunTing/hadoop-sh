package test

import java.{io, util}

import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}
import org.apache.spark.{Partitioner, SparkConf, SparkContext}


/**
  * 自定义累加器
  */

object Spark12_AccumlatorV2 {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    //创建spark上下文对象
    val sc = new SparkContext(config)
    //创建RDD
    //重内存中创建  makeRDD  底层实现 parallelize  两个一样 用makeRDD

    //自定义分区器
    val listRDD = sc.makeRDD(List(("hadoop"), ("spark"), ("sasdfla"), ("poj"), ("scala")))


    //创建累加器对象
    //    val accumulator: LongAccumulator = sc.longAccumulator
    //创建累加器
    val wordAccumulator = new WordAccumulator()
    //注册累加器
    sc.register(wordAccumulator)

//    listRDD.foreach {
//      case word => {
//        wordAccumulator.add(word)
//      }
//    }

    listRDD.foreach(data => {
      wordAccumulator.add(data)
    })
    println(wordAccumulator.value)
  }


}

//自定义累加器

class WordAccumulator extends AccumulatorV2[String, util.ArrayList[String]] {
  private val list = new util.ArrayList[String]()

  override def isZero: Boolean = {
    list.isEmpty
  }

  override def copy(): AccumulatorV2[String, util.ArrayList[String]] = {
    new WordAccumulator()
  }

  override def reset() {
    list.clear()
  }

  override def add(v: String) {
    if (v.contains("s")) {
      list.add(v)
    }
  }

  override def merge(other: AccumulatorV2[String, util.ArrayList[String]]) {
    list.addAll(other.value)
  }

  override def value: util.ArrayList[String] = {
    list
  }
}



