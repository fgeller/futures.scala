object Main extends App {
       new Thread() {
         override def run(): Unit = {
           println("hello, from elsewhere.")
         }
       }.start()
       Thread.sleep(1)
       println("hello, from runner.")
}
