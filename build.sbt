//
// http://spark.apache.org/docs/latest/quick-start.html#a-standalone-app-in-scala
//
name         := "spark-streaming-scala-akka-activator"
organization := "pl.japila"
version      := "1.0"
scalaVersion := Version.scala

libraryDependencies ++= Dependencies.sparkAkkaHadoop
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "2.0.0-SNAPSHOT"

resolvers += "apache-snapshot" at "https://repository.apache.org/content/repositories/snapshots"

scalariformSettings

initialCommands in console := """
  |import org.apache.spark._
  |import org.apache.spark.streaming._
  |import org.apache.spark.streaming.akka.{ ActorReceiver, AkkaUtils }
  |import _root_.akka.actor.{ ActorSystem, Actor, Props }
  |import com.typesafe.config.ConfigFactory
  |""".stripMargin

fork in run := true
javaOptions in run ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")
outputStrategy := Some(StdoutOutput)

