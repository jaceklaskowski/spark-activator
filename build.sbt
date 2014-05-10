//
// http://spark.apache.org/docs/latest/quick-start.html#a-standalone-app-in-scala
//
name := "Spark Activator"

version := "1.0"

// 2.11 doesn't seem to work
scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark"  %% "spark-core"    % "1.0.0-SNAPSHOT",
  "org.apache.hadoop"  % "hadoop-client" % "2.4.0"
)

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"
