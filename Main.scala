object Main extends App {
  def log(msg: String) = println(s"${Thread.currentThread}: $msg")
  class Future[T]() { var value: T = _ }
  object Future {
    def apply[T](v: => T): Future[T] = {
      val result = new Future[T]()
      val thread = new Thread() {
        override def run(): Unit = { result.value = v }
      }
      thread.start()
      result
    }
  }
  log("hello, world.")
  val of: Future[Unit] = Future.apply(log("blubb"))
  log("good bye.")
}
