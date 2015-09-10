package example 

import spray.routing._
import spray.http._
import spray.httpx.SprayJsonSupport._

import scala.concurrent._
import scala.util.{Success,Failure}

/** Method interface for content returned to routes */
trait Controller extends HttpService{
	import MyJSONProtocol._

	def futureThing[A](a: A)(implicit ec: ExecutionContext) : Future[A] = future { a }

	def multiFutureThing[A](a: A)(implicit ec: ExecutionContext) : Future[A] = {
		val thing : Future[A] = for {
			i  <- future {1}
			j  <- future {2}
			thing <- future { a }
		} yield(thing)
		thing
	}
	
	def createThing(model: Thing)(implicit ec: ExecutionContext) : spray.routing.RequestContext => Unit = {
		onComplete(futureThing[Thing](model)) {
			case Success(m) => complete(ResultWrapper[Thing](200,m))
			case Failure(ex) => complete(ex.getMessage())
		}
	}

	def createSubThing(model: SubThing)(implicit ec: ExecutionContext) : spray.routing.RequestContext => Unit = {
		onComplete(futureThing[SubThing](model)) {
			case Success(m) => complete(ResultWrapper[SubThing](200,m))
			case Failure(ex) => complete(ex.getMessage())
		}
	}

	def createSubThing2(model: SubThing2)(implicit ec: ExecutionContext) : spray.routing.RequestContext => Unit ={
		onComplete(futureThing[SubThing2](model)) {
			case Success(m) => complete(ResultWrapper[SubThing2](200,m))
			case Failure(ex) => complete(ex.getMessage())
		}
	}

	def doThing(string: String)(implicit ec: ExecutionContext) : RequestContext => Unit  = {
		onComplete(futureThing[Thing](Thing(string))) {
			case Success(m) => 
				println(s"Thing! $string")
				complete(ResultWrapper[Thing](200,m))
			case Failure(ex) => 
				println(s"Thing fail! $string")
				complete(ex.getMessage())
		}
	}

	def doThing2(string: String)(implicit ec: ExecutionContext) : RequestContext => Unit = {
		onComplete(multiFutureThing[Thing](Thing(string))) {
			case Success(m) => 
				println(s"Thing!! $string!")
				complete(ResultWrapper[Thing](200,m))
			case Failure(ex) => 
				println(s"Thing fail!! $string")
				complete(ex.getMessage())
		}
	}

}
