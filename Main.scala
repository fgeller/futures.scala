object Main extends App {
  import scala.util._
  import scala.concurrent._
  import ExecutionContext.Implicits._

  val data: Seq[Int] = Seq.fill(10)(Random.nextInt)
  val partitions: Seq[Seq[Int]]  = data.grouped(2).toSeq
  def work(is: Seq[Int]): Future[Seq[Int]] = Future(is.filter(_ >= 0))
  val positives: Seq[Future[Seq[Int]]] = partitions.map(work)
  val combined: Future[Seq[Seq[Int]]] = Future.sequence(positives)
  val sum: Future[Long] =
    combined.map(iss => iss.flatten.foldLeft(0L){ (acc, i) => acc + i })

  sum andThen {
    case Success(ps) => println(s"Success! $ps")
  }
  Thread.sleep(10)
}
