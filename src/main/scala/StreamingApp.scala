import java.util.concurrent.Executors
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
      .setMaster("local[*]") // run locally with enough threads
      .setAppName("Spark Streaming with Scala and Akka") // name in Spark web UI
    val ssc = new StreamingContext(conf, Seconds(5))
    val stream: ReceiverInputDStream[(String, String)] = ssc.receiverStream(
      new Receiver[(String, String)](StorageLevel.MEMORY_ONLY_2) {
        lazy val executorPool = Executors.newFixedThreadPool(3)

        def onStart() {
          println("[ACTIVATOR] onStart called")
          executorPool.submit(new Runnable {
            def run() {
              store(("one", "ONE"))
            }
          })
          executorPool.submit(new Runnable {
            def run() {
              store(("two", "TWO"))
            }
          })
          executorPool.submit(new Runnable {
            def run() {
              store(("three", "THREE"))
            }
          })
          stop("Stop the system")
        }

        def onStop() {
          println("[ACTIVATOR] onStop called")
          executorPool.shutdown()
        }
      }
    )
    stream.count().map(cnt => "Received " + cnt + " events.").print()

    ssc.start()
    ssc.awaitTermination()
    //    val stopSparkContext, stopGracefully = true
    //    ssc.stop(stopSparkContext, stopGracefully)
  }
}