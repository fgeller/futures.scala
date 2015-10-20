object Main extends App {
  import scala.util._
  import scala.collection.mutable
  object our {
    trait Promise[T] { def complete(value: Try[T]): Promise[T] }
    trait Future[T] {}
    object Future {
      def apply[T](v: => T): Future[T] = {
        val result = new impl.Promise[T]()
        val thread = new Thread() {
          override def run(): Unit = { result.complete(Try(v)) }
        }
        thread.start()
        result.future
      }
    }

    object impl {
      private[our] class Promise[T] extends Future[T] {
        def future: Future[T] = this
        private var value = Option.empty[Try[T]]
        def complete(v: Try[T]): Promise[T] = {
          this.value = Some(v)
          this
        }
      }
    }
  }

  import our._
  def log(msg: String) = println(s"${Thread.currentThread}: $msg")
  val of: Future[Unit] = Future.apply(log("blubb"))
  Thread.sleep(10)
  log(s"our future: $of")
}
