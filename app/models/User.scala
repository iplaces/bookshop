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
                        sex: String,
                        address: Option[String],
                        age: Option[Int]
                        )
object User{
//  def all():List[User] = DB.withConnection {  implicit c =>
//    SQL("select * from userinfo").as(user *)
//  }
  def create(info:User) {
    DB.withConnection { implicit c =>
      SQL("insert into userinfo (username, userpwd, usermail, usergender, userage) values ({username}, {password}, {email}), {sex}, {age}").on(
        'username -> info.username,
        'password -> info.password,
        'email -> info.email,
        'sex -> info.profile.sex,
        'age -> info.profile.age
      ).executeUpdate()
    }
  }

//  val user = {
//      get[String]("username") ~
//      get[String]("userpwd") ~
//      get[String]("email") ~
//      get[UserProfile]("profile")  map {
//      case username~password~email~profile => User(username, password, email, profile)
//    }
//  }
}
