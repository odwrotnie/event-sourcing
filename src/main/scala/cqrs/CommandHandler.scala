package cqrs

import akka.actor.ActorRef

trait Command { }

abstract class CommandHandler
  extends Handler[Command]
    with HasActorSystem {

  override def name: String = "command-handler"
}
