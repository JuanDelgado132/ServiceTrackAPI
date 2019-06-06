package Models
import anorm._
import anorm.SqlParser.get
import play.api.libs.json._
import play.api.libs.functional.syntax._
case class Client(id: String, firstName: String, lastName: String, gender: String, comments: String, birthday: String)

object Client{
  implicit  val clientWrites = Json.writes[Client]
  implicit val clientReads = Json.reads[Client]

  val mapClientFromDB: RowParser[Client] = {
    get[String]("id") ~
      get[String]("firstName") ~
      get[String]("lastName") ~
    get[String]("gender") ~
    get[String]("comments") ~
    get[String]("birthday") map {
      case id ~ firstName ~ lastName ~ gender ~ comments ~ birthday => Client(id, firstName, lastName, gender, comments, birthday)
    }
  }
}
