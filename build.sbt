//
// http://spark.apache.org/docs/latest/quick-start.html#a-standalone-app-in-scala
//
name := "spark-streaming-scala"

// 2.11 doesn't seem to work
scalaVersion := "2.10.4"

libraryDependencies ++= Dependencies.sparkAkkaHadoop

releaseSettings

scalariformSettings

initialCommands in console := """
  |import org.apache.spark._
  |import org.apache.spark.streaming._
  |import org.apache.spark.streaming.StreamingContext._
  |import org.apache.spark.streaming.dstream._
  |import akka.actor.{ActorSystem, Props}
  |import com.typesafe.config.ConfigFactory
  |""".stripMargin
