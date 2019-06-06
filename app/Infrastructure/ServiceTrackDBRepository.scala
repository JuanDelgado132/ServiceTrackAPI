package Infrastructure
import play.api.db._
import anorm._
import anorm.SqlParser._
import Models.{Client, User}
import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionException

@Singleton
class ServiceTrackDBRepository @Inject()(DB: Database) extends ServiceTrackDBRepositoryTrait {


   override def addNewUser(user: User): Unit = {
     DB.withConnection{implicit connection =>
       SQL("INSERT INTO USERS (id,firstName,lastName,email,admin,address,phone,userPassword)" +
         "VALUES({id},{firstName},{lastName},{email},{admin},{address},{phone},{userPassword})").on(
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
      SQL("UPDATE Users SET firstName = {firstName}, lastName = {lastName},email = {email},admin = {admin},address = {address},phone = {phone},userPassword = {userPassword} WHERE id = {id}").on(
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

  override def deleteUser(id: String): Unit = {
    DB.withConnection{ implicit connection =>
      SQL("DELETE FROM Users WHERE id = {id}").on("id" -> id).execute()
    }
  }

  override def getUser(id: String): User = {
    DB.withConnection{implicit  connection =>
      try {
        SQL("SELECT * FROM Users WHERE id = {id}").on("id" -> {
          id
        }).as(User.mapUserFromDB.single)
      } catch  {
        case ex: AnormException => return null;
      }
    }
  }

  override def addNewClient(client: Client): Unit = {
    DB.withConnection { implicit connection =>

      SQL("INSERT INTO CLIENTS (id,firstName,lastName,gender,comments,birthday)" +
        "VALUES({id},{firstName},{lastName},{gender},{comments},{birthday})").on(
        "id" -> client.id,
        "firstName" -> client.firstName,
        "lastName" -> client.lastName,
        "gender" -> client.gender,
        "comments" -> client.comments,
        "birthday" -> client.birthday
      ).execute()

    }
  }

  override def deleteClient(id: String): Unit = {
    DB.withConnection{implicit  connection =>
      SQL("DELETE FROM CLIENTS WHERE id = {id}").on("id" -> id).execute()
    }
  }

  override def getClient(id: String): Client = {
    DB.withConnection{implicit  connection =>
      try {
        SQL("SELECT * FROM CLIENTS WHERE id = {id}").on("id" -> {
          id
        }).as(Client.mapClientFromDB.single)
      } catch  {
        case ex: AnormException => return null;
      }
    }
  }

  override def updateClient(client: Client): Unit = {
    DB.withConnection{implicit  connection =>
      SQL("UPDATE CLIENTS SET firstName = {firstName}, lastName = {lastName},gender = {gender},comments = {comments},birthday = {birthday} WHERE id = {id}").on(
        "id" -> client.id,
        "firstName" -> client.firstName,
        "lastName" -> client.lastName,
        "gender" -> client.gender,
        "comments" -> client.comments,
        "birthday" -> client.birthday
      ).execute()
    }
  }
}
