package ControllerTest

import Infrastructure.ServiceTrackDBRepository
import Models.{Client, ResponseModel}
import controllers.{ClientsController}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.mvc.{ Results}
import play.api.test.{FakeRequest, Helpers}

import scala.concurrent.Future
import play.api.mvc._
import play.api.test.Helpers._

class ClientsControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Results {

  "ClientsController#ClientDoesNotExist" should {
    "Return an empty Json object" in {
      val clientsController = new ClientsController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val result: Future[Result] = clientsController.getClient("b0425406-4b8c-44f1-9710-f81c20a5ad43").apply(FakeRequest())
      val jsonObj = contentAsJson(result)
      status(result) mustEqual NOT_FOUND
      assert(jsonObj.equals(Json.obj()))
    }


  }
  "ClientsController#ClientExist" should {
    "Return a client object when a client is found" in {
      val clientsController = new ClientsController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val result: Future[Result] = clientsController.getClient("f968e4f5-1afd-44db-883d-1dcd3915d761").apply(FakeRequest())
      status(result) mustEqual OK
      contentAsJson(result) mustEqual Json.toJson(clientsController.repository.getClient("f968e4f5-1afd-44db-883d-1dcd3915d761"))
    }
  }
  "ClientsController#UpdateClient" should {
    "Return the updated client upon success" in {
      val clientsController = new ClientsController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val ogClient = clientsController.repository.getClient("f968e4f5-1afd-44db-883d-1dcd3915d761")
      val updateClient = Client(ogClient.id, ogClient.firstName, "new last name", ogClient.gender, ogClient.comments, ogClient.birthday)
      val result: Future[Result] = clientsController.UpdateClient.apply(FakeRequest().withJsonBody(Json.toJson(updateClient)))
      status(result) mustEqual OK
      contentAsJson(result) mustEqual Json.toJson(updateClient)
    }
  }
  "ClientsController#UpdateClient" should {
    "Bad request upon validation error" in {
      val clientsController = new ClientsController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val client =
        """
            "id":"124232-33234-2324323"
            "firstName": "test"
            "lastName": "sdfsadsadf"
            "validationError" : "error"
          """
      val result: Future[Result] = clientsController.UpdateClient.apply(FakeRequest().withJsonBody(Json.toJson(client)))
      status(result) mustEqual BAD_REQUEST
      contentAsJson(result) mustEqual Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred"))
    }
  }
  "ClientsController#CreateClient" should {
    "Bad request upon validation error" in {
      val clientsController = new ClientsController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val client =
        """
            "id":"124232-33234-2324323"
            "firstName": "test"
            "lastName": "sdfsadsadf"
            "validationError" : "error"
          """
      val result: Future[Result] = clientsController.createClient.apply(FakeRequest().withJsonBody(Json.toJson(client)))
      status(result) mustEqual BAD_REQUEST
      contentAsJson(result) mustEqual Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred"))
    }
  }
  "ClientsController#CreateClient" should {
    "Create on validation success" in {
      val clientsController = new ClientsController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val client = Client("26d75a9c-fade-4570-9b07-785236d28fc3","Jane","Doe","female","comments","1985-02-23")
      val result: Future[Result] = clientsController.createClient.apply(FakeRequest().withJsonBody(Json.toJson(client)))
      status(result) mustEqual CREATED
      contentAsJson(result) mustEqual Json.toJson(Json.toJson(client))
    }
  }
  "ClientsController#GetClientWithServices" should {
    "Return client with services" in {
      val clientsController = new ClientsController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      clientsController.repository.registerService("f968e4f5-1afd-44db-883d-1dcd3915d761","6b397900-e756-4fab-a9ce-3c6ba68c44f1")
      val result: Future[Result] = clientsController.getClientWithServices("f968e4f5-1afd-44db-883d-1dcd3915d761").apply(FakeRequest())
      status(result) mustEqual OK
    }
  }

}
