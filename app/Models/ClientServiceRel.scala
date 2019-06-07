package Models

import akka.io.Tcp.Write
import play.api.libs.json.{JsPath, JsValue, Json, Writes}

case class ClientServiceRel(client: Client, services: Seq[Service])

object ClientServiceRel{
  implicit val clientServiceRelReads = Json.reads[ClientServiceRel]
  implicit  val clientServiceRelWrites: Writes[ClientServiceRel] = (o: ClientServiceRel) => {
   Json.obj(
      "id" -> o.client.id,
      "firstName" -> o.client.firstName,
      "lastName" -> o.client.lastName,
      "gender" -> o.client.gender,
      "comments" -> o.client.comments,
      "birthday" -> o.client.birthday,
      "services" -> o.services
    )
  }
}
