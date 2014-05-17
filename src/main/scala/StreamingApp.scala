import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.storage._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream._
import org.apache.spark.streaming.receiver._

object StreamingApp {
  def main(args: Array[String]) {
    // Configuration for a Spark application.
    // Used to set various Spark parameters as key-value pairs.
    val conf = new SparkConf(false) // skip loading external settings
      .setMaster("local[1]") // run locally with one thread
      .setAppName("Spark Streaming with Scala") // name in Spark web UI
    val ssc = new StreamingContext(conf, Seconds(5))
    val stream: ReceiverInputDStream[String] = ssc.receiverStream(
      new Receiver[String](StorageLevel.MEMORY_ONLY_SER_2) {
        def onStart() {
          println("[ACTIVATOR] onStart called")
          store("one")
          store("two")
          store("three")
          store("four")
          stop("No more data...receiver stopped")
        }

        def onStop() {
          println("[ACTIVATOR] onStop called")
        }
      }
    )
    stream.count().map(cnt => "Received " + cnt + " events.").print()

    ssc.start()
    // ssc.awaitTermination(1000)
    val stopSparkContext, stopGracefully = true
    ssc.stop(stopSparkContext, stopGracefully)
  }
}