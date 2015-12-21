package models

/** ****************************************
  * File name: models
  * Creator: lixin
  * Date   : 2015/12/21
  * Time   : 11:37
  * *****************************************/
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Login(username: String, password: String)

object Login{
  def find(username:String, pwd:String):Boolean = {
    val temp = DB.withConnection { implicit c =>
      SQL("SELECT userpwd FROM userinfo WHERE username = {username}").on(
        'username -> username
      ).as(scalar[String].singleOpt)
    }
    temp.getOrElse("") == pwd && pwd != ""
  }
/*
  val login = {
      get[String]("username") ~
      get[String]("userpwd") map {
      case username~userpwd => Login(username, userpwd)
    }
  }
*/
}


