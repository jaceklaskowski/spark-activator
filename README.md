Spark Streaming with Scala and Akka activator template
======================================================

[![Build Status](https://travis-ci.org/jaceklaskowski/spark-activator.svg?branch=master)](https://travis-ci.org/jaceklaskowski/spark-activator) [![Stories in Ready](https://badge.waffle.io/jaceklaskowski/spark-activator.png?label=ready&title=Ready)](https://waffle.io/jaceklaskowski/spark-activator)

This is an [Typesafe Activator](http://typesafe.com/platform/getstarted) template to demonstrate [Apache Spark](http://spark.apache.org) for near-real-time data stream processing using [Scala](http://www.scala-lang.org/) and [Akka](http://akka.io/) using the [Spark Streaming](http://spark.apache.org/docs/latest/streaming-programming-guide.html) extension.

The motivation was [Go Reactive Activator Contest: Scala Days Edition](http://typesafe.com/blog/go-reactive-activator-contest-scala-days-edition)

# Run the activator project

Once you download the activator template, execute `./activator clean run`.

When the words `Hello from Spark Streaming with Scala and Akka` arrive to the actor and Spark Streaming gets notified about them (by the `store` method), the flow finishes and so does the demo.

Watch out for the following message in the logs (the `Time` line may show a different value):

    -------------------------------------------
    Time: 1422714451000 ms
    -------------------------------------------
    Hello from Spark Streaming with Scala and Akka

Please note that the `clean` task is important after the first run as some files get serialized and then deserialized that have often caused deserialization problems. As a workaround, running `clean` gets rid of the potential issue quickly and easily.
