object Main extends App {
  import scala.util._
  import scala.collection.mutable

  object our {
    trait Promise[T] { def complete(value: Try[T]): Promise[T] }
    trait Future[T] {
      def onComplete(fun: Try[T] => Unit): Unit
      def map[U](fun: T => U): Future[U] = {
        val result = new impl.Promise[U]()
        this.onComplete {
          case Success(v)  => result.complete(Try(fun(v)))
          case Failure(th) => log("uhoh... not evaluating fun")
        }
        result.future
      }
    }
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
        private var onCompletes = mutable.Set.empty[Try[T] => Unit]
        def onComplete(fun: Try[T] => Unit): Unit = { onCompletes += fun }
        def complete(v: Try[T]): Promise[T] = {
          this.value = Some(v)
          this.onCompletes.foreach(_(v))
          this
        }
      }
    }
  }

  import our._
  def log(msg: String) = println(s"${Thread.currentThread}: $msg")
  log("hello, world.")
  val f = Future(21)
    .map(value => value / 0)
    .map(value => log(s"our future: ${value}"))
  Thread.sleep(1)
}
