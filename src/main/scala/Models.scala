package example

case class Thing(str: String)

case class SubThing(thingId: Int, prop: Int)

case class SubThing2(thingId: Int, other: String, opt: Option[String] = None)

case class ResultWrapper[A](stat: Int, result: A)

import spray.json._

object MyJSONProtocol extends DefaultJsonProtocol {
	implicit val thingConv = jsonFormat1(Thing)
	implicit val thingConv2 = jsonFormat2(SubThing)
	implicit val thingConv3 = jsonFormat3(SubThing2)

	implicit val res1Conv = jsonFormat2(ResultWrapper[Thing])
	implicit val res2Conv = jsonFormat2(ResultWrapper[SubThing])
	implicit val res3Conv = jsonFormat2(ResultWrapper[SubThing2])
}