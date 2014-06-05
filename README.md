Spark Streaming with Scala and Akka activator template
======================================================

[![Build Status](https://travis-ci.org/jaceklaskowski/spark-activator.svg?branch=master)](https://travis-ci.org/jaceklaskowski/spark-activator)

This is an [Typesafe Activator](http://typesafe.com/platform/getstarted) template to demonstrate [Apache Spark](http://spark.apache.org) for near-real-time data streaming with Scala and Akka using the [Spark Streaming](http://spark.apache.org/docs/latest/streaming-programming-guide.html) extension.

The motivation: [Go Reactive Activator Contest: Scala Days Edition](http://typesafe.com/blog/go-reactive-activator-contest-scala-days-edition)

# Run the activator project

Once you download the activator template, execute `sbt ';clean;runMain StreamingApp'`. `clean` is important after the first run when some files get serialized and then deserialized that may in turn cause deserialization problems. As a workaround, running `clean` gets rid of the potential issue quickly and easily.
