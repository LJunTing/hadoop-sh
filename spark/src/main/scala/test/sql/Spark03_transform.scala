package test.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object Spark03_transform {

  def main(args: Array[String]): Unit = {
    //2.3  session  创建方式
    val spark = SparkSession.builder().master("local[*]")
      .appName("sqlTest").getOrCreate()
    //进行转换之前需要引入隐式转换规则
    import spark.implicits._
    //创建rdd
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1, "zhangsan", 21), (2, "lisi", 12), (3, "wangwu", 56)))
    //转换为df
    val rdd_to_df: DataFrame = rdd.toDF("id", "name", "age")
    //转换为ds
    val df_to_ds: Dataset[User] = rdd_to_df.as[User]
    //转换为df
    val ds_to_df: DataFrame = df_to_ds.toDF()
    //转换为rdd
    val rdd2: RDD[Row] = ds_to_df.rdd

    rdd2.foreach(row => {
      println(row.getInt(0), row.getString(1), row.getInt(2))
    })
    spark.stop()

  }

}

//样例类
case class User(id: Int, name: String, age: Int)