package test.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 数据:
  *
  * 1516667 河南 xxx
  * 1516669 河北 xxx
  * 1516669 河南 yyy
  * 1516667 河南 xxx
  * 1516667 河北 yyy
  * 1516667 河南 xxx
  * 1516667 河南 aaa
  * 1516667 河南 ccc
  * 1516667 河北 aaa
  * 1516667 河北 ccc
  * 1516667 河南 xxx
  * 1516667 河南 xxx
  * 1516667 河北 yyy
  * 1516667 河北 aaa
  * 1516669 河南 yyy
  * 1516667 河北 yyy
  * 1516667 河北 yyy
  * 1516667 河南 ccc
  * 1516669 河南 yyy
  */
object test_ad_touch {

  def main(args: Array[String]): Unit = {
    val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Wordcount")
    val sc = new SparkContext(config)

    val lineRDD: RDD[String] = sc.textFile("in")


    //切分  转换为 (city-ad,touchnum) 数据
    val format_data: RDD[(String, Int)] = lineRDD.map((lines => {
      val line_worrds: Array[String] = lines.split(" ")
      val city: String = line_worrds(1)
      val adTouch: String = line_worrds(2)
      //结果 转换  k-v
      (city + "-" + adTouch, 1)
    }))
    //
    val reduceRDD: RDD[(String, Int)] = format_data.reduceByKey(_ + _)

    //----------------------(河南-xxx,点击总数)---------------------------------

    //数据转换   叠加点击次数  后的数据
    //(河南-xxx,5)
    val group: RDD[(String, String, Int)] = reduceRDD.map(f = data => {
      val city_ad: String = data._1
      val touch_num: Int = data._2

      val strings: Array[String] = city_ad.split("-")
      val city: String = strings(0)
      val ad: String = strings(1)
      //有了总点击后  拆分city-ad   转换   k-v-v
      (city, ad, touch_num)

    })
    // map(河南,xxx,5)
    //根据省份 分组
    val city_group: RDD[(String, Iterable[(String, String, Int)])] = group.groupBy(key => key._1)
    //对于么每个组内的  数据按点击排序
    val topn: RDD[(String, String)] = city_group.map(touch_num => {
      val touch_sort: List[String] = touch_num._2.toList.sortBy(_._3)(Ordering.Int.reverse).take(3).map(item => item._2 + "广告 被点击了=" + item._3)
      (touch_num._1, "前三的广告:" + touch_sort)
    })
    topn.foreach(println)
  }


}
