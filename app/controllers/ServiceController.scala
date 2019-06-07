package controllers

import Infrastructure.ServiceTrackDBRepository
import Models.Service
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsSuccess, Json}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ServiceController @Inject() (cc: ControllerComponents, repository: ServiceTrackDBRepository) extends AbstractController(cc){

  def createNewService() = Action { implicit request =>
    request.body.asJson.get.validate[Service] match {
      case success: JsSuccess[Service] => {
        val serviceToCreate = success.get
        repository.addNewService(serviceToCreate)
        Created(Json.toJson(repository.getService(serviceToCreate.id)))
      }
      case error => {
        BadRequest("A validation error has occurred")
      }
    }
  }
  def deleteService(id: String) = Action{
    repository.deleteService(id)
    Ok("Success deleting service")
  }
  def getService(id: String) = Action{
    val service = repository.getService(id)
    if(service == null)
      NotFound(s"Service ${id} was not found")
    else
      Ok(Json.toJson(service))
  }
  def updateService() = Action { implicit request =>
    request.body.asJson.get.validate[Service] match {
      case success: JsSuccess[Service] => {
        val service = success.get
        repository.updateService(service)
        Ok(Json.toJson(repository.getService(service.id)))
      }
      case error => {
        BadRequest("A validation error has occured")
      }
    }
  }

}