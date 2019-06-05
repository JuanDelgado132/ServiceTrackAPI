package controllers

import Infrastructure.ServiceTrackDBRepository
import Models.User
import javax.inject._
import play.api.libs.json.{JsPath, Writes}
import play.api.mvc._
import play.api.libs.json._ // JSON library
import play.api.libs.json.Reads._ // Custom validation helpers
import play.api.libs.functional.syntax._


@Singleton
class UsersController @Inject() (cc: ControllerComponents, repository: ServiceTrackDBRepository) extends AbstractController(cc){
  implicit val userWrites: Writes[User] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "firstName").write[String] and
      (JsPath \ "lastName").write[String] and
      (JsPath \ "email").write[String] and
      (JsPath \ "admin").write[Boolean] and
      (JsPath \ "address").write[String] and
      (JsPath \ "phone").write[String] and
      (JsPath \ "userPassword").write[String])(unlift(User.unapply))


  def getUser(id: Int) = Action {


    Ok(Json.toJson(repository.getUser(id)))
  }

}
