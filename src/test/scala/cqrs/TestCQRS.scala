package cqrs

import akka.actor.ActorSystem
import org.scalatest.FlatSpec

case class CreateNote(value: String) extends Command
case class CreatedNote(value: String) extends Event

object Commands
  extends CommandHandler
    with Settings {
  override val handleReceive: PartialFunction[Any, Unit] = {
    case CreateNote(value) =>
      println(s"Create note command: $value")
      Events.handle(CreatedNote(value))
  }
}

object Events
  extends EventHandler
    with Settings {
  override val handleReceive: PartialFunction[Any, Unit] = {
    case CreatedNote(value) =>
      println(s"Created note event: $value")
  }
}

class TestCQRS
  extends FlatSpec {

  "Command" should "generate event" in {
    Commands.handle(CreateNote("ASDF"))
  }
}

object Settings
  extends HasActorSystem {
  val system = ActorSystem("s")
}
trait Settings
  extends HasActorSystem {
  val system = Settings.system
}
