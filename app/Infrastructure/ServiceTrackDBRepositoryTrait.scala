package Infrastructure
import Models.{Client, ClientServiceRel, ListServices, Service, User}
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[ServiceTrackDBRepository])
trait ServiceTrackDBRepositoryTrait {

  def addNewUser(user: User)
  def getUser(id: String): User
  def deleteUser(id: String)
  def updateUser(user: User)
  def addNewClient(client: Client)
  def getClient(id: String) : Client
  def updateClient(client: Client)
  def deleteClient(id: String)
  def addNewService(service: Service)
  def getService(id: String) : Service
  def updateService(service: Service)
  def deleteService(id: String)
  def registerService(clientId: String, serviceId: String)
  def getClientWithServices(clientId: String): ClientServiceRel
  def getUnregisteredServices(): ListServices
  def getRegisteredServices(): ListServices


}
