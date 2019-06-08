package Models

import play.api.libs.json.Writes
import play.api.libs.json._

case class ResponseModel(code: Int, message: String)

object ResponseModel{
  implicit val reponseWrite = Json.writes[ResponseModel]
}