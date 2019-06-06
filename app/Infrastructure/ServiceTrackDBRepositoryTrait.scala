package Infrastructure
import Models.{Client, User}
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

}
