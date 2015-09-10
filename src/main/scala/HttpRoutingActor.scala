package example

import akka.actor.{Actor,Props,ActorLogging}
import akka.routing._
import spray.routing._
import spray.http._
import spray.httpx.SprayJsonSupport._
import MediaTypes._
import akka.pattern.ask
import scala.util.{Success,Failure, Try}
import scala.concurrent.duration._
import scala.concurrent._
import akka.util.Timeout


class HttpRoutingActor extends Actor with ActorLogging with Controller with ThingRoutes{
  def actorRefFactory = context
  private implicit def executionContext = actorRefFactory.dispatcher

  import MyJSONProtocol._

  /** Respond to messages with runRoute on the routes provided in this class */
  def receive = runRoute(myRoutes)

}