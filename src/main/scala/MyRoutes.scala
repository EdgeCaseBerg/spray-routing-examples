package example

import akka.actor.{Actor,Props,ActorLogging}
import akka.routing._
import spray.routing._
import spray.http._
import spray.httpx.SprayJsonSupport._
import MediaTypes._
import akka.pattern.ask
import scala.util.{Success,Failure, Try}
import scala.util.matching.Regex
import scala.concurrent.duration._
import scala.concurrent._
import akka.util.Timeout

trait ThingRoutes extends HttpService with Controller{ 
	import MyJSONProtocol._
 
	def myRoutes(implicit executor: ExecutionContext) = {
		pathPrefix("v0" / "things") {
			post {
				pathPrefix(new Regex("[a-zA-Z0-9]*")) { thingId =>
					pathPrefix("something") {
						pathEnd {
							entity(as[SubThing]) { subThing =>
								createSubThing(subThing)
							}
						}
					} ~ 
					pathPrefix("thingstuff") {
						pathEnd {
							entity(as[SubThing2]) { subThing2 =>
								createSubThing2(subThing2)
							}
						}
					}
				} ~ 
				pathEndOrSingleSlash {
					entity(as[Thing]) { thing =>
						createThing(thing) 
					}
				}
			} ~ 
			get {
				pathPrefix(new Regex("[a-zA-Z0-9]*")) { thingId =>
					pathPrefix("derived"/ IntNumber) { thing2Id => 						
						path("generated"/ IntNumber) { thirdThing =>
							pathEnd {
								doThing(s"$thirdThing $thing2Id")
							}
						} ~ 
						pathEnd{
							doThing2(s"$thingId, $thing2Id")
						}								
					} ~ 
					pathEnd {
						doThing(thingId)           
					}
				}
			}
		}

	}
}