package test.sql

import org.apache.spark.sql.{DataFrame, SparkSession}

object Spark02_sql {

  def main(args: Array[String]): Unit = {
    //2.3  session  创建方式
    val session = SparkSession.builder().master("local[*]")
      .appName("sqlTest").getOrCreate()

    val testDf: DataFrame = session.read.json("in/test.json")

    //将dataframe 转换为一张表
    testDf.createOrReplaceTempView("user")

    session.sql("select * from user").show()

    session.stop()
  }
}
