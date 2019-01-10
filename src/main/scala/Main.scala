
object Main {
  def main(args: Array[String]): Unit = {

    import com.typesafe.config.ConfigFactory

    val conf = ConfigFactory.load
    val globalValue = conf.getString("myservice.globalValue")
    val environmentValue = conf.getString("myservice.environmentValue")
    val overwriteableValue = conf.getString("myservice.overwriteableValue")

    val someHost = conf.getString("myservice.someConf.host")
    val somePort = conf.getString("myservice.someConf.port")

    val message =
      s"""
        | globalValue: $globalValue
        | environmentValue: $environmentValue
        | overwritableValue: $overwriteableValue
        | someHost: $someHost
        | somePort: $somePort
      """.stripMargin

    while (true) {
      Thread.sleep(10000)
      println(message)
    }
  }
}
