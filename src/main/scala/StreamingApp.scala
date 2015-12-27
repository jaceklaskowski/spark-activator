import akka.actor.{ Actor, Props }
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.receiver._

class Helloer extends Actor with ActorHelper {
  override def preStart() = {
    println("")
    println("=== Helloer is starting up ===")
    println(s"=== path=${context.self.path} ===")
    println("")
  }
  def receive = {
    // store() method allows us to store the message so Spark Streaming knows about it
    // This is the integration point (from Akka's side) between Spark Streaming and Akka
    case s => store(s)
  }
}

object StreamingApp {
  def main(args: Array[String]) {
    // Configuration for a Spark application.
    // Used to set various Spark parameters as key-value pairs.
    val driverPort = 7777
    val driverHost = "localhost"
    val conf = new SparkConf(false) // skip loading external settings
      .setMaster("local[*]") // run locally with as many threads as CPUs
      .setAppName("Spark Streaming with Scala and Akka") // name in web UI
      .set("spark.logConf", "true")
      .set("spark.driver.port", driverPort.toString)
      .set("spark.driver.host", driverHost)
      .set("spark.akka.logLifecycleEvents", "true")
    val ssc = new StreamingContext(conf, Seconds(1))
    val actorName = "helloer"

    // This is the integration point (from Spark's side) between Spark Streaming and Akka system
    // It's expected that the actor we're now instantiating will `store` messages (to close the integration loop)
    val actorStream = ssc.actorStream[String](Props[Helloer], actorName)

    // describe the computation on the input stream as a series of higher-level transformations
    actorStream.reduce(_ + " " + _).print()

    // start the streaming context so the data can be processed
    // and the actor gets started
    ssc.start()

    // FIXME wish I knew a better way to handle the asynchrony
    java.util.concurrent.TimeUnit.SECONDS.sleep(3)

    val actorSystem = SparkEnv.get.actorSystem

    val url = s"akka.tcp://sparkDriver@$driverHost:$driverPort/user/Supervisor0/$actorName"
    val helloer = actorSystem.actorSelection(url)
    helloer ! "Hello"
    helloer ! "from"
    helloer ! "Spark Streaming"
    helloer ! "with"
    helloer ! "Scala"
    helloer ! "and"
    helloer ! "Akka"

    scala.io.StdIn.readLine("Press Enter to stop Spark Streaming context and the application...")
    ssc.stop(stopSparkContext = true, stopGracefully = true)
  }
}
