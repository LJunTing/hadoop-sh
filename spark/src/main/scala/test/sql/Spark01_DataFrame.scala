package test.sql

import org.apache.spark.sql.{DataFrame, SparkSession}

object Spark01_DataFrame {

  def main(args: Array[String]): Unit = {
    //2.3  session  创建方式
    val session = SparkSession.builder().master("local[*]")
      .appName("sqlTest").getOrCreate()

    val testDf: DataFrame = session.read.json("in/test.json")

    testDf.show()

    session.stop()
  }

}
