package Infrastructure
import Models.User
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[ServiceTrackDBRepository])
trait ServiceTrackDBRepositoryTrait {

  def addNewUser(user: User)
  def getUser(id: String): User
  def deleteUser(id: String)
  def updateUser(user: User)

}
