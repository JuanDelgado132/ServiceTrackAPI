package ControllerTest

import Infrastructure.ServiceTrackDBRepository
import Models.{ResponseModel, User}
import akka.stream.Materializer
import controllers.UsersController
import javax.inject.Inject
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.mvc.{DefaultActionBuilder, Results}
import play.api.test.{FakeRequest, Helpers}

import scala.concurrent.Future
import play.api.mvc._
import play.api.test.Helpers._

class UsersControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Results {
  implicit lazy val materializer: Materializer = app.materializer
  implicit lazy val Action = app.injector.instanceOf(classOf[DefaultActionBuilder])
  "UsersController#userDoesNotExist" should {
    "Return an empty Json object" in {
      val usersController = new UsersController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val result: Future[Result] = usersController.getUser("b0425406-4b8c-44f1-9710-f81c20a5ad43").apply(FakeRequest())
      val jsonObj = contentAsJson(result)
      status(result) mustEqual NOT_FOUND
      assert(jsonObj.equals(Json.obj()))
    }


  }
  "UsersController#UserExist" should {
    "Return a user object when a user is found" in {
      val usersController = new UsersController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val result: Future[Result] = usersController.getUser("9ee0293b-36c7-4ae3-ad7a-c83e8afcbde6").apply(FakeRequest())
      status(result) mustEqual OK
      contentAsJson(result) mustEqual Json.toJson(usersController.repository.getUser("9ee0293b-36c7-4ae3-ad7a-c83e8afcbde6"))
    }
  }
  "UsersController#UpdateUser" should {
    "Return the updated user upon success" in {
      val usersController = new UsersController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val originalUser = usersController.repository.getUser("9ee0293b-36c7-4ae3-ad7a-c83e8afcbde6")
      val updateUser = User(originalUser.id, originalUser.firstName, "new last name", originalUser.email, originalUser.admin, originalUser.address, originalUser.phone, originalUser.userPassword)
      val result: Future[Result] = usersController.UpdateUser.apply(FakeRequest().withJsonBody(Json.toJson(updateUser)))
      status(result) mustEqual OK
      contentAsJson(result) mustEqual Json.toJson(updateUser)
    }
  }
  "UsersController#UpdateUser" should {
    "Bad request upon validation error" in {
      val usersController = new UsersController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val user =
        """
            "id":"124232-33234-2324323"
            "firstName": "test"
            "lastName": "sdfsadsadf"
            "validationError" : "error"
          """
      val result: Future[Result] = usersController.UpdateUser.apply(FakeRequest().withJsonBody(Json.toJson(user)))
      status(result) mustEqual BAD_REQUEST
      contentAsJson(result) mustEqual Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred"))
    }
  }
  "UsersController#CreateUser" should {
    "Bad request upon validation error" in {
      val usersController = new UsersController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val user =
        """
            "id":"124232-33234-2324323"
            "firstName": "test"
            "lastName": "sdfsadsadf"
            "validationError" : "error"
          """
      val result: Future[Result] = usersController.CreateNewUser.apply(FakeRequest().withJsonBody(Json.toJson(user)))
      status(result) mustEqual BAD_REQUEST
      contentAsJson(result) mustEqual Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred"))
    }
  }
  "UsersController#CreateUser" should {
    "Create on validation success" in {
      val usersController = new UsersController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val user = User("26d75a9c-fade-4570-9b07-289569d28fc3","Jane","Doe","johndoe@gmail.com",false,"945 Main Street","9872253698","12qwaszX")
      val result: Future[Result] = usersController.CreateNewUser.apply(FakeRequest().withJsonBody(Json.toJson(user)))
      status(result) mustEqual CREATED
      contentAsJson(result) mustEqual Json.toJson(Json.toJson(user))
    }
  }

}
