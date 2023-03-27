package test.exercise

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object in03 {
  def main(args: Array[String]): Unit = {
    val session: SparkSession = SparkSession.builder().master("local[*]").appName("in03").getOrCreate()
    val context: SparkContext = session.sparkContext

    //键值对的key 表示图书名称，value表示某天图书销量，请计算每个键对应的平均值，也就是计算每种图书的每天平均销量。
    val tuples = Array(("spark", 2), ("hadoop", 6), ("hadoop", 4), ("spark", 6))
    val rdd: RDD[(String, Int)] = context.parallelize(tuples)

    val tuple: (String, Int, Int) = rdd.map {
      case (name, sales) => {
        (name, sales, 1)
      }
    }
      .reduce {
        case (x, y) => {
          if (x._1.equals(y._1)) {
            (x._1, x._2 + y._2, x._3 + y._3)
          }else{
            (y._1, x._2 + y._2, x._3 + y._3)
          }
        }
      }
    println(tuple._2/tuple._3)


  }

}
