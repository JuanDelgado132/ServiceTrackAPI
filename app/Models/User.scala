package Models

import play.api.db._
import anorm._

case class User(id :Int, firstName: String, lastName: String, email: String, admin: Boolean, address: String, phone: String, userPassword: String)

