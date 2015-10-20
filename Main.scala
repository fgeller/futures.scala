object Main extends App {
  import scala.util._
  def log(msg: String) = println(s"${Thread.currentThread}: $msg")
  class Future[T]() { var value = Option.empty[Try[T]] }
  object Future {
    def apply[T](v: => T): Future[T] = {
      val result = new Future[T]()
      val thread = new Thread() {
        override def run(): Unit = { result.value = Some(Try(v)) }
      }
      thread.start()
      result
    }
  }
  log("hello, world.")
  val of: Future[Unit] = Future.apply(log("blubb"))
  Thread.sleep(10)
  log(s"our future: ${of.value}")
  log("good bye.")
}
