package pl.japila.spark.streaming

import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.storage.StorageLevel

case class CustomReceiverInputDStream[T](override val storageLevel: StorageLevel) extends Receiver[T](storageLevel) {
  def onStart() {
    println("\nHello from CustomReceiver.START\n")
  }

  def onStop() {
    println("\nHello from CustomReceiver.STOP\n")
  }
}
