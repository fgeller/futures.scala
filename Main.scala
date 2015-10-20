object Main extends App {
  import scala.util._
  import scala.concurrent._
  import ExecutionContext.Implicits._

  val data: Seq[Int] = Seq.fill(10)(Random.nextInt)
  val partitions: Seq[Seq[Int]]  = data.grouped(2).toSeq
  def work(is: Seq[Int]): Future[Seq[Int]] =
    Future(is.map(_ / (Random.nextInt % 3)))
  val positives: Seq[Future[Seq[Int]]] = partitions.map(work)
  val combined: Future[Seq[Try[Seq[Int]]]] =
    Future.traverse(positives){ is: Future[Seq[Int]] =>
      is.map(Success(_)).recover({ case th => Failure(th) })
    }

  combined andThen {
    case Success(ps) => println(s"Success! ${ps.toList}")
    case Failure(th) => println(s"Failure! $th")
  }
  Thread.sleep(10)
}
