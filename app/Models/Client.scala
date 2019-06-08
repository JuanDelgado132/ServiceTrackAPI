package Models
import anorm._
import anorm.SqlParser.get
import play.api.libs.json._
case class Client(id: String, firstName: String, lastName: String, gender: String, comments: String, birthday: String)

object Client{
  implicit val clientReads = Json.reads[Client]
  implicit val clientWrites: Writes[Client] = (client: Client) => {
    if(client == null){
      Json.obj()
    }
    else{
      Json.obj(
        "id" -> client.id,
        "firstName" -> client.firstName,
        "lastName" -> client.lastName,
        "gender" -> client.gender,
        "comments" -> client.comments,
        "birthday" -> client.birthday
      )
    }

  }

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
