package controllers

import Infrastructure.ServiceTrackDBRepository
import Models.{ResponseModel, User}
import javax.inject._
import play.api.mvc._
import play.api.libs.json._ // JSON library

@Singleton
class UsersController @Inject() (cc: ControllerComponents, val repository: ServiceTrackDBRepository) extends AbstractController(cc){

  def getUser(id: String) = Action{
    val user = repository.getUser(id)
    if(user == null)
      NotFound(Json.toJson(user))
    else
      Ok(Json.toJson(user))
  }
  def DeleteUser(id: String) = Action {
    repository.deleteUser(id)
    Ok(Json.toJson(ResponseModel(OK, "Successfully deleted user")))
  }

  def CreateNewUser = Action { implicit request =>
    request.body.asJson.get.validate[User] match {
      case s: JsSuccess[User] => {
        val userToCreate = s.get
        repository.addNewUser(userToCreate)
        Created(Json.toJson(repository.getUser(userToCreate.id)))
      }
      case e: JsError => {
        BadRequest(Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred")))
      }
    }
  }
  def UpdateUser = Action { implicit request =>
    request.body.asJson.get.validate[User] match {
      case s: JsSuccess[User] => {
        val userToUpdate = s.get
        println(userToUpdate)
        repository.updateUser(userToUpdate)
        Ok(Json.toJson(repository.getUser(userToUpdate.id)))
      }
      case e: JsError => {
        BadRequest(Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred")))
      }
    }
  }

}
