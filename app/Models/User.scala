package Models
import anorm._
import anorm.SqlParser.get
import play.api.libs.json._



case class User(id :String, firstName: String, lastName: String, email: String, admin: Boolean, address: String, phone: String, userPassword: String)

object User{
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
  implicit val userWrites: Writes[User] = (user: User) => {
    if(user == null){
      Json.obj()
    }
    else{
      Json.obj(
        "id" -> user.id,
        "firstName" -> user.firstName,
        "lastName" -> user.lastName,
        "email" -> user.email,
        "admin" -> user.admin,
        "address" -> user.address,
        "phone" -> user.phone,
        "userPassword" -> user.userPassword
      )
    }
  }

  implicit  val userReads = Json.reads[User]


}