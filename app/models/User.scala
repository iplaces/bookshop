package models

/** ****************************************
  * File name: models
  * Creator: lixin
  * Date   : 2015/12/21
  * Time   : 10:05
  * *****************************************/
import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class User(
                 username: String,
                 password: String,
                 email: String,
                 profile: UserProfile
                 )

case class UserProfile(
                        sex: Int,
                        telephone: Option [String],
                        age: Option[Int]
                        )

object User{
  def all():List[User] = DB.withConnection {  implicit c =>
    SQL("select * from userinfo").as(user *)
  }
  def create(info:User) {
    DB.withConnection { implicit c =>
      SQL("insert into userinfo (username, userpwd, usermail, usergender, usertel, userage) values ({username}, {password}, {email}, {sex}, {telephone}, {age})").on(
        'username -> info.username,
        'password -> info.password,
        'email -> info.email,
        'sex -> info.profile.sex,
        'telephone -> info.profile.telephone,
        'age -> info.profile.age
      ).executeUpdate()
    }
  }

  def userCheck(username:String):Boolean = {
      if(all().count(_.username == username) == 1) {
        false
      } else {
        true
      }
  }

  val user = {

      get[String]("username") ~
      get[String]("userpwd") ~
      get[String]("usermail") ~
      get[Int]("usergender") ~
      get[Option[String]]("usertel") ~
      get[Option[Int]]("userage")  map {
        case username~userpwd~usermail~usergender~usertel~userage => User(username, userpwd, usermail, UserProfile(usergender, usertel, userage))
    }

  }
}
