object Main extends App {
  import scala.concurrent._
  import ExecutionContext.Implicits._
  val f: Future[Unit] =
    Future { println("hello, from elsewhere.") }

  Thread.sleep(1)
  println("hello, from runner.")
}
