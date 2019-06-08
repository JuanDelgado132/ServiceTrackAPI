package Models

import anorm._
import anorm.SqlParser.get
import anorm.RowParser
import play.api.libs.json.{Json, Writes}

case class Service(serviceId: String, serviceName: String, serviceDescription: String, days: String, time: String, active: Boolean)

object  Service{
  implicit val serviceReads = Json.reads[Service]
  implicit val serviceWrites: Writes[Service] = (service: Service) => {
    if(service == null){
      Json.obj()
    }
    else {
      Json.obj(
        "serviceId" -> service.serviceId,
        "serviceName" -> service.serviceName,
        "serviceDescription" -> service.serviceDescription,
        "days" -> service.days,
        "time" -> service.time,
        "active" -> service.active

      )
    }
  }

  val mapServiceFromDB: RowParser[Service] = {
    get[String]("serviceId") ~
    get[String]("serviceName") ~
    get[String]("serviceDescription") ~
    get[String]("days") ~
    get[String]("time") ~
    get[Boolean]("active") map {
      case serviceId ~ serviceName ~ serviceDescription ~ days ~ time ~ active => Service(serviceId, serviceName, serviceDescription, days, time, active)
      case null => null
    }
  }
}
