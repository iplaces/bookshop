package controllers

/** ****************************************
  * File name: controllers
  * Creator: lixin
  * Date   : 2015/12/21
  * Time   : 9:58
  * *****************************************/
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import views._

import models._

object SignUp extends Controller{

  val signupForm: Form[User] = Form(

    mapping(
      "username" -> text(minLength = 4),
      "email" -> email,

      "password" -> tuple(
        "main" -> text(minLength = 6),
        "confirm" -> text
      ).verifying(
        "Passwords don't match", passwords => passwords._1 == passwords._2
      ),

      "profile" -> mapping(
        "country" -> nonEmptyText,
        "address" -> optional(text),
        "age" -> optional(number(min = 1, max = 100))
      )

      (UserProfile.apply)(UserProfile.unapply),

      "accept" -> checked("You must accept the conditions")

    )

    {
      (username, email, passwords, profile, _) => User(username, passwords._1, email, profile)
    }
    {
      user => Some(user.username, user.email, (user.password, ""), user.profile, false)
    }.verifying(
      "This username is not available",
      user => !Seq("admin", "guest").contains(user.username)
    )
  )


  def form = Action {
    Ok(html.signup.form(signupForm));
  }

  /**
   * Display a form pre-filled with an existing User.
   */
  def editForm = Action {
    val existingUser = User(
      "fakeuser", "secret", "fake@gmail.com",
      UserProfile("France", None, Some(30))
    )
    Ok(html.signup.form(signupForm.fill(existingUser)))
  }

  /**
   * Handle form submission.
   */
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => BadRequest(html.signup.form(errors)),

      // We got a valid User value, display the summary
      user => Ok(html.signup.form(signupForm))   //Ok(html.signup.summary(user)   //源代码为显示提交信息，这里需要修改
    )
  }

}