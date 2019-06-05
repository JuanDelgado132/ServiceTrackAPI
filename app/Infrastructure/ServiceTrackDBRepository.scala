package Infrastructure
import play.api.db._
import anorm._
import anorm.SqlParser._
import Models.User
import javax.inject.{Inject, Singleton}

@Singleton
class ServiceTrackDBRepository @Inject()(DB: Database) extends ServiceTrackDBRepositoryTrait {

  val userMap: RowParser[User] = {
    get[Int]("id") ~
    get[String]("firstName") ~
    get[String]("lastName") ~
    get[String]("email") ~
    get[Boolean]("admin") ~
    get[String]("address") ~
    get[String]("phone") ~
    get[String]("userPassword") map {case id ~ firstName ~ lastName ~ email ~ admin ~ address ~ phone ~ userPassword => User(id, firstName, lastName, email, admin, address, phone, userPassword)}

  }
   override def addNewUser(user: User): Unit = {
     DB.withConnection{implicit connection =>
       SQL("INSERT INTO USERS (id,first_name,last_name,email,admin,address,phone,user_password)" +
         "VALUES({id},{first_name},{last_name},{email},{admin},{address},{phone},{user_password})").on(
         "id" -> user.id,
         "firstName" -> user.firstName,
         "lastName" -> user.lastName,
         "email" -> user.email,
         "admin" -> user.admin,
         "address" -> user.address,
         "phone" -> user.phone,
         "userPassword" -> user.userPassword
       ).execute()
     }
   }

  override def updateUser(user: User): Unit = {
    DB.withConnection{implicit connection =>
      SQL("UPDATE Users SET id = {id}," +
        "first_name = {firstName}," +
        "last_name = {lastName}," +
        "email = {email}," +
        "admin = {admin}," +
        "address = {address}," +
        "phone = {phone}," +
        "user_password = {userPassword}").on(
        "id" -> user.id,
        "first_name" -> user.firstName,
        "last_name" -> user.lastName,
        "email" -> user.email,
        "admin" -> user.admin,
        "address" -> user.address,
        "phone" -> user.phone,
        "user_password" -> user.userPassword
      ).execute()
    }
  }

  override def deleteUser(id: Int): Unit = {
    DB.withConnection{ implicit connection =>
      SQL("DELETE FROM Users WHERE id = {id}").on("id" -> id).execute()
    }
  }

  override def getUser(id: Int): User = {
    DB.withConnection{implicit  connection =>
      SQL("SELECT * FROM Users WHERE id = {id}").on("id" -> {id}).as(userMap.single )
    }

  }
}
