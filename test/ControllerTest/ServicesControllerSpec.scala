package ControllerTest

import Infrastructure.ServiceTrackDBRepository
import Models.{Client, ResponseModel, Service}
import controllers.{ClientsController, ServiceController}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.mvc.{DefaultActionBuilder, Results}
import play.api.test.{FakeRequest, Helpers}

import scala.concurrent.Future
import play.api.mvc._
import play.api.test.Helpers._

class ServicesControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Results {

  "ServicesController#ServiceDoesNotExist" should {
    "Return an empty Json object" in {
      val servicesController = new ServiceController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val result: Future[Result] = servicesController.getService("b0425406-4b8c-44f1-9710-f81c20a5ad43").apply(FakeRequest())
      val jsonObj = contentAsJson(result)
      status(result) mustEqual NOT_FOUND
      assert(jsonObj.equals(Json.obj()))
    }


  }
  "ServicesController#ServicesExist" should {
    "Return a Service object when a service is found" in {
      val servicesController = new ServiceController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val result: Future[Result] = servicesController.getService("6b397900-e756-4fab-a9ce-3c6ba68c44f1").apply(FakeRequest())
      status(result) mustEqual OK
      contentAsJson(result) mustEqual Json.toJson(servicesController.repository.getService("f968e4f5-1afd-44db-883d-1dcd3915d761"))
    }
  }
  "ServicesController#ServiceClient" should {
    "Return the updated service upon success" in {
      val servicesController = new ServiceController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val ogService = servicesController.repository.getService("6b397900-e756-4fab-a9ce-3c6ba68c44f1")
      val updateService = Service(ogService.serviceId, ogService.serviceName, "new description", ogService.days, ogService.time, ogService.active)
      val result: Future[Result] = servicesController.updateService.apply(FakeRequest().withJsonBody(Json.toJson(updateService)))
      status(result) mustEqual OK
      contentAsJson(result) mustEqual Json.toJson(updateService)
    }
  }
  "ServicesController#ServicesClient" should {
    "Bad request upon validation error" in {
      val servicesController = new ServiceController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val service =
        """
            "id":"124232-33234-2324323"
            "serviceName": "test"
            "serviceDescription": "sdfsadsadf"
            "validationError" : "error"
          """
      val result: Future[Result] = servicesController.updateService().apply(FakeRequest().withJsonBody(Json.toJson(service)))
      status(result) mustEqual BAD_REQUEST
      contentAsJson(result) mustEqual Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred"))
    }
  }
  "ServicesController#CreateService" should {
    "Bad request upon validation error" in {
      val servicesController = new ServiceController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val service =
        """
          "id":"124232-33234-2324323"
          "serviceName": "test"
          "serviceDescription": "sdfsadsadf"
          "validationError" : "error"
          """
      val result: Future[Result] = servicesController.createNewService.apply(FakeRequest().withJsonBody(Json.toJson(service)))
      status(result) mustEqual BAD_REQUEST
      contentAsJson(result) mustEqual Json.toJson(ResponseModel(BAD_REQUEST, "Validation error occurred"))
    }
  }
  "ServicesController#CreateServices" should {
    "Create on validation success" in {
      val servicesController = new ServiceController(Helpers.stubControllerComponents(), app.injector.instanceOf(classOf[ServiceTrackDBRepository]))
      val service = Service("26d75a9c-fade-4570-9b07-451369d28fc3","New Service","new service desc","M-F","1-2",true)
      val result: Future[Result] = servicesController.createNewService.apply(FakeRequest().withJsonBody(Json.toJson(service)))
      status(result) mustEqual CREATED
      contentAsJson(result) mustEqual Json.toJson(Json.toJson(service))
    }
  }

}
