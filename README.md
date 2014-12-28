Spark Streaming with Scala and Akka activator template
======================================================

[![Build Status](https://travis-ci.org/jaceklaskowski/spark-activator.svg?branch=master)](https://travis-ci.org/jaceklaskowski/spark-activator) [![Stories in Ready](https://badge.waffle.io/jaceklaskowski/spark-activator.png?label=ready&title=Ready)](https://waffle.io/jaceklaskowski/spark-activator)

This is an [Typesafe Activator](http://typesafe.com/platform/getstarted) template to demonstrate [Apache Spark](http://spark.apache.org) for near-real-time data streaming with Scala and Akka using the [Spark Streaming](http://spark.apache.org/docs/latest/streaming-programming-guide.html) extension.

The motivation: [Go Reactive Activator Contest: Scala Days Edition](http://typesafe.com/blog/go-reactive-activator-contest-scala-days-edition)

# Run the activator project

Once you download the activator template, execute `./activator clean 'runMain StreamingApp'`.

Please note that the `clean` task is important after the first run as some files get serialized and then deserialized that have often caused deserialization problems. As a workaround, running `clean` gets rid of the potential issue quickly and easily.
