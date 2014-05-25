import akka.actor.{ Actor, Props }
import java.util.concurrent.Executors
import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.storage._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream._
import org.apache.spark.streaming.receiver._

class Helloer extends Actor with ActorHelper {
  def receive = {
    case s: String => {
      println(s"Received: $s")
      store(s)
    }
  }
}

object StreamingApp {
  def main(args: Array[String]) {
    // Configuration for a Spark application.
    // Used to set various Spark parameters as key-value pairs.
    val conf = new SparkConf(false) // skip loading external settings
      .setMaster("local[*]") // run locally with enough threads
      .setAppName("Spark Streaming with Scala and Akka") // name in Spark web UI
      .set("spark.logConf", "true")
    val ssc = new StreamingContext(conf, Seconds(5))
    val actorStream: ReceiverInputDStream[String] = ssc.actorStream[String](Props[Helloer], "helloer")
    actorStream.count().map(cnt => "Received " + cnt + " events.").print()

    ssc.start()
    ssc.awaitTermination()
    //    val stopSparkContext, stopGracefully = true
    //    ssc.stop(stopSparkContext, stopGracefully)
  }
}