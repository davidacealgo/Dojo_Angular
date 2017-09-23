package models

import com.sun.org.apache.xpath.internal.operations.Or
import play.api.libs.json.{JsPath, Json, Reads}
import play.api.libs.functional.syntax._

case class Place(id: Int, name: String, description:Option[String])

object Place {

  implicit val placeWrite = Json.writes[Place]
  implicit val placeRead = Json.reads[Place]

}
