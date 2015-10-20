object Main extends App {
  import scala.util._
  import scala.concurrent._
  import ExecutionContext.Implicits._
  def log(msg: String) = println(s"${Thread.currentThread}: $msg")

  def readFile(fn: String): Future[Map[String, Int]] =
    Future{
      log("reading file")
      Map("hans" -> 21, "peter" -> 42)
    }
  def updateCounts(mp: Map[String, Int]): Future[Map[String, Int]] =
    Future{
      log("updating counts")
      mp.map({ case (n, c) => n -> (c + Random.nextInt) }).toMap
    }

  log("about to start reading")
  val f: Future[Map[String, Int]] =
    for {
      oc <- readFile("stats.txt")
      uc <- updateCounts(oc)
    } yield uc

  f andThen {
    case Success(uc) => log(s"got updated counts: $uc")
  }

  Thread.sleep(1)
}
