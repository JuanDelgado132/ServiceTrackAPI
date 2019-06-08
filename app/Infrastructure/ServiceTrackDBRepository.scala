package Infrastructure

import play.api.db._
import anorm._
import Models.{Client, ClientServiceRel, ListServices, Service, User}
import javax.inject.{Inject, Singleton}
import  java.util.UUID
@Singleton
class ServiceTrackDBRepository @Inject()(DB: Database) extends ServiceTrackDBRepositoryTrait {


  override def addNewUser(user: User): Unit = {

    DB.withConnection { implicit connection =>
      val id = if (user.id == null) generateUniqueId() else user.id
      SQL("INSERT INTO USERS (id,firstName,lastName,email,admin,address,phone,userPassword)" +
        "VALUES({id},{firstName},{lastName},{email},{admin},{address},{phone},{userPassword})").on(
        "id" -> id,
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
    DB.withConnection { implicit connection =>
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
    DB.withConnection { implicit connection =>
      SQL("DELETE FROM Users WHERE id = {id}").on("id" -> id).execute()
    }
  }

  override def getUser(id: String): User = {
    DB.withConnection { implicit connection =>
      val result = SQL("SELECT * FROM Users WHERE id = {id}").on("id" -> {
        id
      }).executeQuery()
      val option = result.as(User.mapUserFromDB.singleOpt)
      if (option.isEmpty)
        null
      else
        option.get


    }
  }

  override def addNewClient(client: Client): Unit = {
    val id = if (client.id == null) generateUniqueId() else client.id

    DB.withConnection { implicit connection =>

      SQL("INSERT INTO CLIENTS (id,firstName,lastName,gender,comments,birthday)" +
        "VALUES({id},{firstName},{lastName},{gender},{comments},{birthday})").on(
        "id" -> id,
        "firstName" -> client.firstName,
        "lastName" -> client.lastName,
        "gender" -> client.gender,
        "comments" -> client.comments,
        "birthday" -> client.birthday
      ).execute()

    }
  }

  override def deleteClient(id: String): Unit = {
    DB.withConnection { implicit connection =>
      SQL("DELETE FROM CLIENTS WHERE id = {id}").on("id" -> id).execute()
    }
  }

  override def getClient(id: String): Client = {
    DB.withConnection { implicit connection =>

      val result = SQL("SELECT * FROM CLIENTS WHERE id = {id}").on("id" -> {
        id
      }).executeQuery()

      val option = result.as(Client.mapClientFromDB.singleOpt)
      if (option.isEmpty)
        null
      else
        option.get
    }
  }

  override def updateClient(client: Client): Unit = {
    DB.withConnection { implicit connection =>
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

  override def addNewService(service: Service): Unit = {
    val id = if (service.serviceId == null) generateUniqueId() else service.serviceId
    DB.withConnection { implicit connection =>
      SQL("INSERT INTO SERVICES (serviceId,serviceName,serviceDescription,days,time,active)" +
        "VALUES({serviceId},{serviceName},{serviceDescription},{days},{time},{active})").on(
        "serviceId" -> id,
        "serviceName" -> service.serviceName,
        "serviceDescription" -> service.serviceDescription,
        "days" -> service.days,
        "time" -> service.time,
        "active" -> service.active
      ).execute()
    }
  }

  override def getService(id: String): Service = {
    DB.withConnection { implicit connection =>

      val result = SQL("SELECT * FROM SERVICES WHERE serviceId = {serviceId}").on("serviceId" -> {
        id
      }).executeQuery()
      val option = result.as(Service.mapServiceFromDB.singleOpt)
      if (option.isEmpty)
        null
      else
        option.get


    }
  }

  override def deleteService(id: String): Unit = {
    DB.withConnection { implicit connection =>
      SQL("DELETE FROM SERVICES WHERE serviceId = {serviceId}").on("serviceId" -> {
        id
      }).execute()
    }
  }

  override def updateService(service: Service): Unit = {
    DB.withConnection { implicit connection =>
      SQL("UPDATE SERVICES SET serviceName = {serviceName}, serviceDescription = {serviceDescription}, days = {days}, time = {time}, active = {active} WHERE serviceId = {serviceId}").on(
        "serviceId" -> service.serviceId,
        "serviceName" -> service.serviceName,
        "serviceDescription" -> service.serviceDescription,
        "days" -> service.days,
        "time" -> service.time,
        "active" -> service.active
      ).execute()

    }
  }

  override def registerService(clientId: String, serviceId: String): Unit = {
    DB.withConnection { implicit connection =>
      SQL("INSERT INTO ClientServiceRel (id, serviceId)VALUES({id},{serviceId})").on("id" -> clientId, "serviceId" -> serviceId)
        .execute()
    }
  }

  override def getClientWithServices(clientId: String): ClientServiceRel = {
    val client = getClient(clientId)
    DB.withConnection { implicit connection =>
      val serviceQuery = SQL("SELECT * FROM SERVICES WHERE serviceId IN (SELECT serviceId FROM ClientServiceRel)")
      val services: Seq[Service] = serviceQuery.as(Service.mapServiceFromDB *)
      ClientServiceRel(client, services)
    }
  }

  override def getUnregisteredServices(): ListServices = {
    DB.withConnection { implicit connection =>
      val serviceQuery = SQL("SELECT * FROM SERVICES WHERE serviceId NOT IN (SELECT serviceId FROM ClientServiceRel)")
      val result = serviceQuery.executeQuery()
      val services: Seq[Service] = serviceQuery.as(Service.mapServiceFromDB *)
      ListServices(services)

    }
  }

  override def getRegisteredServices(): ListServices = {
    DB.withConnection { implicit connection =>
      val serviceQuery = SQL("SELECT * FROM SERVICES WHERE serviceId  IN (SELECT serviceId FROM ClientServiceRel)")
      val servicesResult = serviceQuery.executeQuery()
      val services: Seq[Service] = servicesResult.as(Service.mapServiceFromDB *)
      ListServices(services)

    }
  }

  def generateUniqueId(): String  = {
    UUID.randomUUID().toString
  }
}
