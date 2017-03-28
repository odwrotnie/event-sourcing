import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume}
import akka.actor._

import scala.concurrent.duration.DurationInt

case class Number(value: Int)

object ActorFails extends App {

  class WantEventNumber extends Actor with ActorLogging {

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 second) {
      case _: ArithmeticException => Restart
      // case _: NullPointerException => Restart
      case _: Exception => Escalate
    }

    override def receive: Receive = {
      case n: Number if (n.value % 2) == 0 =>
        log.info(s"I have got ${ n.value }")
      case n: Number if (n.value % 2) != 0 =>
        throw new ArithmeticException("I wanted even number you piece of shit!")
    }
  }

  val system = ActorSystem("Want")
  val wanter = system.actorOf(Props[WantEventNumber], "wanter")

  2 to 10 foreach { i =>
    wanter ! Number(i)
  }
}
