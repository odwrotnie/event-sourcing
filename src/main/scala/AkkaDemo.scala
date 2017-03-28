import akka.actor._

case object Ball

object AkkaDemo extends App {

  class Ping extends Actor with ActorLogging {
    var counter = 0
    override def receive: Receive = {
      case Ball =>
        counter += 1
        log.info(s"Ping received ball for the $counter time")
        Thread.sleep(1000)
        sender() ! Ball
    }
  }

  class Pong extends Actor with ActorLogging {
    var counter = 0
    override def receive: Receive = {
      case Ball =>
        counter += 1
        log.info(s"Pong received ball for the $counter time")
        Thread.sleep(1000)
        sender() ! Ball
    }
  }

  val system = ActorSystem("Pingpong")

  val ping = system.actorOf(Props[Ping], "ping")
  val pong = system.actorOf(Props[Pong], "pong")

  ping.tell(Ball, pong) // Instead of ping ! Ball since we need the sender
}
