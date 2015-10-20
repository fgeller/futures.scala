object Main extends App {
  import scala.util._
  import scala.concurrent._
  import ExecutionContext.Implicits._

  def readFile(fn: String): Future[Map[String, Int]] =
    Future(Map("hans" -> 21, "peter" -> 42))
  def updateCounts(mp: Map[String, Int]): Future[Map[String, Int]] =
    Future(mp.map({ case (n, c) => n -> (c + Random.nextInt) }).toMap)

  val f: Future[Map[String, Int]] =
    for {
      oc <- readFile("stats.txt")
      uc <- updateCounts(oc)
    } yield uc

  f andThen {
    case Success(uc) => println(s"got updated counts: $uc")
  }

  Thread.sleep(2)
}
