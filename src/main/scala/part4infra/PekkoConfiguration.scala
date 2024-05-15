package part4infra

import org.apache.pekko.actor.typed.{ActorSystem, Behavior}
import org.apache.pekko.actor.typed.scaladsl.Behaviors
import com.typesafe.config.ConfigFactory

object PekkoConfiguration {

  object SimpleLoggingActor {
    def apply(): Behavior[String] = Behaviors.receive { (context, message) =>
      context.log.info(message)
      Behaviors.same
    }
  }

  // 1 - inline configuration
  def demoInlineConfig(): Unit = {
    // HOCON, superset of JSON, managed by Lightbend
    val configString: String =
      """
        |pekko {
        |  loglevel = "DEBUG"
        |}
        |""".stripMargin
    val config = ConfigFactory.parseString(configString)
    val system = ActorSystem(SimpleLoggingActor(), "ConfigDemo", ConfigFactory.load(config))

    system ! "A message to remember"

    Thread.sleep(1000)
    system.terminate()
  }

  // 2 - config file
  def demoConfigFile(): Unit = {
    val specialConfig = ConfigFactory.load().getConfig("mySpecialConfig")
    val system = ActorSystem(SimpleLoggingActor(), "ConfigDemo", specialConfig)

    system ! "A message to remember"

    Thread.sleep(1000)
    system.terminate()
  }

  // 3 - a different config in another file
  def demoSeparateConfigFile(): Unit = {
    val separateConfig = ConfigFactory.load("secretDir/secretConfiguration.conf")
    println(separateConfig.getString("pekko.loglevel"))
  }

  // 4 - different file formats (JSON, properties)
  def demoOtherFileFormats(): Unit = {
    val jsonConfig = ConfigFactory.load("json/jsonConfiguration.json")
    println(s"json config with custom property: ${jsonConfig.getString("aJsonProperty")}")
    println(s"json config with Pekko property: ${jsonConfig.getString("pekko.loglevel")}")

    // properties format
    val propsConfig = ConfigFactory.load("properties/propsConfiguration.properties")
    println(s"properties config with custom property: ${propsConfig.getString("mySimpleProperty")}")
    println(s"properties config with Pekko property: ${propsConfig.getString("pekko.loglevel")}")
  }

  def main(args: Array[String]): Unit = {
    demoOtherFileFormats()
  }
}
