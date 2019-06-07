package Models

import play.api.libs.json.{Json, Writes}

case class ListServices(services: Seq[Service])

object ListServices{
  implicit val servicesWrites: Writes[ListServices] = (list: ListServices) => {

    Json.obj(
      "services" -> list.services
    )

  }
}
