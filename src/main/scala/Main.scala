
object Main {
  def main(args: Array[String]): Unit = {

    import com.typesafe.config.ConfigFactory

    val conf = ConfigFactory.load
    val a = conf.getString("myservice.a")
    val b = conf.getString("myservice.b")
    val c = conf.getString("myservice.c")

    val message =
      s"""
        | a: $a
        | b: $b
        | c: $c
      """.stripMargin

    while (true) {
      println(message)
      Thread.sleep(100000)
    }
  }
}
