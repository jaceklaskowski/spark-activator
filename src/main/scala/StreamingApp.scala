import akka.actor.{ Actor, Props }
import java.util.concurrent.Executors
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.storage._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream._
import org.apache.spark.streaming.receiver._
import scala.concurrent.Await

class Helloer extends Actor with ActorHelper {
  override def preStart() = {
    println("")
    println("=== Helloer is starting up ===")
    println(s"=== path=${context.self.path} ===")
    println("")
  }
  def receive = {
    case s: String =>
      store(s)
  }
}

object StreamingApp {
  def main(args: Array[String]) {
    // Configuration for a Spark application.
    // Used to set various Spark parameters as key-value pairs.
    val driverPort = 7777
    val driverHost = "localhost"
    val conf = new SparkConf(false) // skip loading external settings
      .setMaster("local[*]") // run locally with enough threads
      .setAppName("Spark Streaming with Scala and Akka") // name in Spark web UI
      .set("spark.logConf", "true")
      .set("spark.driver.port", s"$driverPort")
      .set("spark.driver.host", s"$driverHost")
      .set("spark.akka.logLifecycleEvents", "true")
    val ssc = new StreamingContext(conf, Seconds(1))
    val actorName = "helloer"
    val actorStream: ReceiverInputDStream[String] = ssc.actorStream[String](Props[Helloer], actorName)
    actorStream.print()

    ssc.start()
    Thread.sleep(3 * 1000) // wish I knew a better way to handle the asynchrony

    import scala.concurrent.duration._
    val actorSystem = SparkEnv.get.actorSystem
    val url = s"akka.tcp://spark@$driverHost:$driverPort/user/Supervisor0/$actorName"
    val timeout = 100 seconds
    val helloer = Await.result(actorSystem.actorSelection(url).resolveOne(timeout), timeout)
    helloer ! "Hello"
    helloer ! "from"
    helloer ! "Apache Spark (Streaming)"
    helloer ! "and"
    helloer ! "Akka"
    helloer ! "and"
    helloer ! "Scala"

    val stopSparkContext = true
    val stopGracefully = true
    ssc.stop(stopSparkContext, stopGracefully)
  }
}