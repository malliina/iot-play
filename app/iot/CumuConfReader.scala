package iot

import java.nio.file.Path

import com.mle.file.StorageFile
import com.mle.util.BaseConfigReader

/**
 * @author Michael
 */
object CumuConfReader extends BaseConfigReader[CumuConf] {
  override def userHomeConfPath: Path = userHome / "keys" / "cumulocity.conf"

  override def resourceCredential: String = "fixme"

  override def loadOpt: Option[CumuConf] = fromEnvOpt orElse fromUserHomeOpt

  override def fromMapOpt(map: Map[String, String]): Option[CumuConf] = for {
    url <- map get "url"
    user <- map get "user"
    pass <- map get "pass"
  } yield CumuConf(url, user, pass)
}

case class CumuConf(url: String, user: String, pass: String)
