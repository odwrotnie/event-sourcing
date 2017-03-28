import akka.actor._

case class Greetings(name: String)

object GreeterBecomesAngry extends App {

  class Greeter extends Actor with ActorLogging {

    var counter = 0

    override def receive = nice

    def nice: Receive = {
      case g: Greetings =>
        counter += 1
        log.info(s"Hello ${ g.name } for the $counter time")
        if (counter > 5) context become angry
    }

    def angry: Receive = {
      case g: Greetings =>
        log.info(s"Fuck you ${ g.name }")
    }
  }

  val system = ActorSystem("Greetings")
  val greeter = system.actorOf(Props[Greeter], "greeter")

  1 to 10 foreach { i =>
    greeter ! Greetings("Pawel")
  }
}
