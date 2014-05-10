import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object SimpleApp {
  def main(args: Array[String]) {
    val logFile = "README.md"
    val sc = new SparkContext("local", "Simple App", "~/oss/spark",
      List("target/scala-2.10/spark-activator_2.10-1.0.jar"))
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(_.contains("a")).count()
    val numBs = logData.filter(_.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
  }
}
