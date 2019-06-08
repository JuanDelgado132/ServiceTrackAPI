package controllers

import Infrastructure.ServiceTrackDBRepository
import Models.{Client, ResponseModel, User}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ClientsController @Inject() (cc: ControllerComponents, val repository: ServiceTrackDBRepository) extends AbstractController(cc){

  def getClient(id: String) = Action {
    val client = repository.getClient(id)
    if(client == null)
      NotFound(Json.toJson(client))
    else
      Ok(Json.toJson(client))
  }
  def createClient() = Action { implicit request =>
      request.body.asJson.get.validate[Client] match {
        case s: JsSuccess[Client] => {
          val clientToCreate = s.get
          repository.addNewClient(clientToCreate)
          Created(Json.toJson(repository.getClient(clientToCreate.id)))
        }
        case e: JsError => {
          BadRequest(Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred")))
        }
      }
  }
  def UpdateClient() = Action { implicit request =>
      request.body.asJson.get.validate[Client] match {
        case s: JsSuccess[Client] => {
          val clientToUpdate = s.get
          repository.updateClient(clientToUpdate)
          Ok(Json.toJson(repository.getClient(clientToUpdate.id)))
        }
        case e: JsError => {
          BadRequest(Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred")))
        }
      }
  }

  def DeleteClient(id: String)  = Action {
    repository.deleteClient(id)
    Ok(Json.toJson(ResponseModel(OK, "Client deleted")))
  }

  def getClientWithServices(clientId: String) = Action{
    val clientServiceRel = repository.getClientWithServices(clientId)
    Ok(Json.toJson(clientServiceRel))
  }

}
