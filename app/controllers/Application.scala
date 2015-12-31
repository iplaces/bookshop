package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action { implicit request =>
    val user = request.session.get("username")
    user match {
      case None=> Ok(views.html.index(""))
      case Some(a) => Ok(views.html.index(a))
    }
  }

  def logout = Action {
    Redirect(routes.Application.index()).withSession(
      "username" -> ""
    )
  }

}