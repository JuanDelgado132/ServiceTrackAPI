package controllers

import Infrastructure.ServiceTrackDBRepository
import Models.User
import javax.inject._
import play.api.mvc._
import play.api.libs.json._ // JSON library

@Singleton
class UsersController @Inject() (cc: ControllerComponents, repository: ServiceTrackDBRepository) extends AbstractController(cc){

  def getUser(id: String) = Action{
    val user = repository.getUser(id)
    if(user == null)
      Ok(s"User ${id} does not exist")
    else
      Ok(Json.toJson(user))
  }
  def DeleteUser(id: String) = Action {
    repository.deleteUser(id)
    Ok("User successfully deleted")
  }

  def CreateNewUser = Action { implicit request =>
    request.body.asJson.get.validate[User] match {
      case s: JsSuccess[User] => {
        val userToCreate = s.get
        repository.addNewUser(userToCreate)
        Created(Json.toJson(repository.getUser(userToCreate.id)))
      }
      case e: JsError => {
        BadRequest("A validation error occured")
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
        BadRequest("A validation error occured")
      }
    }
  }

}
