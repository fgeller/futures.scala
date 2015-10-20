object Main extends App {
  class Future[T](value: T)
  object Future {
    def apply[T](v: T): Future[T] = {
      new Future(v)
    }
  }
  println("hello, world.")
  val of: Future[Unit] = Future.apply(println("blubb"))
  println("good bye.")
}
