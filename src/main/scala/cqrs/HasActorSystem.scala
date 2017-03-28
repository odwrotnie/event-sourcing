package cqrs

import akka.actor.ActorSystem

trait HasActorSystem {

  def system: ActorSystem
}
