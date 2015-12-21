package models

/** ****************************************
  * File name: models
  * Creator: lixin
  * Date   : 2015/12/21
  * Time   : 10:05
  * *****************************************/

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
