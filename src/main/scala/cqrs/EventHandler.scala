package cqrs

trait Event { }

trait EventHandler
  extends Handler[Event]
    with HasActorSystem {

  override def name: String = "event-handler"
}
