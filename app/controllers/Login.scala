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

  val loginForm = Form(

    mapping(
        "username" -> nonEmptyText,
        "password" -> nonEmptyText
    )
    {
      (username, password) => Login(username, password)
    }
    {
      login => Some(login.username, login.password)
    }.verifying(
      "This username is not exits",
      login => !Seq("guest").contains(login.username)
    )
  )

  def submits = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      errors => BadRequest(html.login.form(errors, true)),
      labels => {
        val user = labels.username
        val pass = labels.password

        if(Login.find(user) != null && labels.password==Login.find(user).password){
          val temp = Login.find(user)
          Redirect(routes.Application.index()).withSession(
            "username" -> temp.username,
            "timestamp" -> System.currentTimeMillis.toString
          )
        } else {
          Ok(html.login.form(loginForm, false))
        }
      }
    )
  }

  def form = Action {
    Ok(html.login.form(loginForm, true))
  }

  //显示页面
  def show(user:String) = TODO

}