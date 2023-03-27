package test

object scala_test {
  def main(args: Array[String]): Unit = {

    val listRDD: List[Int] = test()
    listRDD.foreach {
      case (data) => {

      }
    }


    for (li <- listRDD) {

    }
    println()
  }

  def mothed(f: Any): Unit = {
    println(f)
  }

  def test(): List[Int] = {
    List(1, 2, 3, 4, 5, 6)
  }

}
