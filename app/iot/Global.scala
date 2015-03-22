package iot

import play.api.{Application, GlobalSettings}
import rx.lang.scala.Observable
import concurrent.duration.DurationInt

/**
 * @author Michael
 */
object Global extends GlobalSettings {
  val interval = Observable.interval(60.seconds)
  override def onStart(app: Application): Unit = {
    super.onStart(app)
    CumulocityClient.init()
  }

  override def onStop(app: Application): Unit = {
    CumulocityClient.close()
    super.onStop(app)
  }
}
