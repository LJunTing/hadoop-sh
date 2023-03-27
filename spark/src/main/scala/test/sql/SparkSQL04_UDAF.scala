package test.sql

import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

object SparkSQL04_UDAF {

  def main(args: Array[String]): Unit = {


    //2.3  spark  创建方式
    val spark: SparkSession = SparkSession.builder().master("local[*]")
      .appName("sqlTest").getOrCreate()
    //进行转换之前需要引入隐式转换规则
    val testDf: DataFrame = spark.read.json("in/test.json")

    //自定义聚合儿函数
    val udaf = new pj
    //注册聚合函数
    spark.udf.register("avegAge", udaf)
    testDf.createOrReplaceTempView("user")
    spark.sql("select avegAge(age) from user").show()

    spark.stop()
  }

}

//声明用户自定义聚合函数
class pj extends UserDefinedAggregateFunction {
  //函数输入的数据结构
  override def inputSchema: StructType = {
    new StructType().add("age", LongType)
  }

  //计算是的数据结构
  override def bufferSchema: StructType = {
    new StructType().add("sum", LongType).add("count", LongType)
  }

  //函数返回值类型
  override def dataType: DataType = DoubleType

  //函数是否稳定
  override def deterministic: Boolean = true

  //计算之前的缓冲区的初始化
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }

  //更新
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    println("更新数据")
    buffer(0) = buffer.getLong(0) + input.getLong(0)
    buffer(1) = buffer.getLong(1) + 1
  }

  //将多个节点的缓冲区合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    println("合并节点数据")
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  //计算
  override def evaluate(buffer: Row): Any = {
    println("计算数据")
    buffer.getLong(0).toDouble / buffer.getLong(1).toDouble
  }
}