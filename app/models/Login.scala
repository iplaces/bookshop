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

case class Login(
                  //userid: Integer,
                  username: String,
                  password: String
                  )

object Login{
  //选择列表中的所有元素
  def all():List[Login] = DB.withConnection {  implicit c =>
    SQL("select * from userinfo").as(login *)
  }
  //查找特定元素
  def find(username:String):Login = {
    if(all().count(_.username == username) == 1) {
        val temp = DB.withConnection { implicit c =>
          SQL("SELECT * FROM userinfo WHERE username = {username}").on(
            'username -> username
          ).as(login *)
        }
        temp.head
      } else {
      null
    }
  }

  val login = {

      get[String]("username") ~
      get[String]("userpwd") map {
      case username~userpwd => Login( username, userpwd)
    }
  }

}


