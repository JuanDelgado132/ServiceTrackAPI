package Models
import anorm._
import java.util.UUID
import anorm.SqlParser.get
import play.api.libs.json._
import play.api.libs.functional.syntax._



case class User(id :String, firstName: String, lastName: String, email: String, admin: Boolean, address: String, phone: String, userPassword: String)

object User{
  def GenerateUniqueId(): String = {
    UUID.randomUUID().toString()
  }
  val mapUserFromDB: RowParser[User] = {
    get[String]("id") ~
      get[String]("firstName") ~
      get[String]("lastName") ~
      get[String]("email") ~
      get[Boolean]("admin") ~
      get[String]("address") ~
      get[String]("phone") ~
      get[String]("userPassword") map {case id ~ firstName ~ lastName ~ email ~ admin ~ address ~ phone ~ userPassword => User(id, firstName, lastName, email, admin, address, phone, userPassword)}
  }
  implicit val userWrites: Writes[User] = (
    (JsPath \ "id").write[String] and
      (JsPath \ "firstName").write[String] and
      (JsPath \ "lastName").write[String] and
      (JsPath \ "email").write[String] and
      (JsPath \ "admin").write[Boolean] and
      (JsPath \ "address").write[String] and
      (JsPath \ "phone").write[String] and
      (JsPath \ "userPassword").write[String])(unlift(User.unapply))

  implicit  val userReads = Json.reads[User]


}