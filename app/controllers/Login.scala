package controllers

/** ****************************************
  * File name: controllers
  * Creator: lixin
  * Date   : 2015/12/21
  * Time   : 10:54
  * *****************************************/
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.data.Form

import views._

import models._

object LogIn extends Controller{

  val loginForm: Form[Login] = Form(

    mapping(
        "username" -> nonEmptyText,
        "password" -> nonEmptyText
    )
    {
      (username, password) => Login(username, password)
    }
    {
      login => Some(login.username, login.password)
    }.verifying("ERROR", result => result match {
      case(username,password) => Login.authenticate(username, password)
    })
//      verifying(
//      "This username is not exits",
//      login => !Seq("guest").contains(login.username)
//    )
  )

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest().fold(
      errors => BadRequest(html.login.form(errors)),
      user => {
        val u = Login.getByemail(user._1).get
        val data = new scala.collection.mutable.HashMap[String, String]
        data("admin_lastlogin") = (System.currentTimeMillis() / 1000).toString
        data("admin_loginip") = request.remoteAddress
        Login.updateById(u.id, data.toMap)
        Redirect(routes.Tasks.show()).withSession(
          SessionKey.ip -> request.remoteAddress,
          SessionKey.timestamp -> System.currentTimeMillis.toString,
          SessionKey.userId -> u.id
        )
      }
    )
  }

  var user:String = null
  var pass:String = null
  def submits = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      errors => BadRequest(html.login.form(errors)),
      labels => {
        user = labels.username
        pass = labels.password
      }
    )
    println(user)
    println(pass)
    if(Login.find(user, pass) == true){
      Redirect(routes.Application.index)
    } else {
      Redirect(routes.LogIn.form)
    }

  }

  def form = Action {
    Ok(html.login.form(loginForm));
  }

  def submit = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => BadRequest(html.login.form(errors)),

      // We got a valid User value, display the summary
      user => Ok(html.login.form(loginForm))   //Ok(html.signup.summary(user)   //源代码为显示提交信息，这里需要修改
    )
  }

  //显示页面
  def show(id:Integer) = TODO
}
