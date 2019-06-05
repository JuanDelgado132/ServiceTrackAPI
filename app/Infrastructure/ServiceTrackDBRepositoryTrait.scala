package Infrastructure
import Models.User
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[ServiceTrackDBRepository])
trait ServiceTrackDBRepositoryTrait {

  def addNewUser(user: User)
  def getUser(id: Int): User
  def deleteUser(id: Int)
  def updateUser(user: User)

}
