package cqrs

import akka.actor._

trait Handler[T] {

  self: HasActorSystem =>

  def name: String

  def handle(t: T) = handler ! t

  def handleReceive: PartialFunction[Any, Unit]

  lazy val handler: ActorRef = system.actorOf(Props(new HandlerActor), name)
  class HandlerActor
    extends Actor
      with ActorLogging {
    override def receive: Receive = handleReceive
  }

}
