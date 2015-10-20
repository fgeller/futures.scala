object Main extends App {
  import scala.util._
  def log(msg: String) = println(s"${Thread.currentThread}: $msg")
  class Promise[T]() { var value = Option.empty[Try[T]] }
  object Promise {
    def apply[T](v: => T): Promise[T] = {
      val result = new Promise[T]()
      val thread = new Thread() {
        override def run(): Unit = { result.value = Some(Try(v)) }
      }
      thread.start()
      result
    }
  }
  log("hello, world.")
  val of: Promise[Unit] = Promise.apply(log("blubb"))
  Thread.sleep(10)
  log(s"our promise: ${of.value}")
}
