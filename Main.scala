object Main extends App {
  def log(msg: String) = println(s"${Thread.currentThread}: $msg")
  class Future[T](value: T)
  object Future {
    def apply[T](v: T): Future[T] = { new Future(v) }
  }
  log("hello, world.")
  val of: Future[Unit] = Future.apply(log("blubb"))
  log("good bye.")
}
