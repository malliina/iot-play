package iot

import c8y.IsDevice
import com.cumulocity.model.authentication.CumulocityCredentials
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation
import com.cumulocity.sdk.client.PlatformImpl
import com.mle.util.Log
import rx.lang.scala.{Observable, Subscription}

import scala.collection.JavaConversions._
import scala.concurrent.duration.DurationInt

/**
 * @author Michael
 */
class CumulocityClient(conf: CumuConf) extends Log {
  val platform = new PlatformImpl(conf.url, new CumulocityCredentials(conf.user, conf.pass))
  val inventory = platform.getInventoryApi

  def devices(): Seq[ManagedObjectRepresentation] = {
    platform.getInventoryApi.getManagedObjects.get().allPages().toSeq
  }

  def registerDevice(name: String): Unit = {
    val mo = new ManagedObjectRepresentation()
    mo.setName(name)
    mo.set(new IsDevice)
    val createdMO = inventory.create(mo)
    log info s"Created: ${createdMO.getSelf}"
  }

  def updateDevice(name: String) = {
    devices().find(_.getName == name).foreach(mo => {
      inventory.update(mo)
    })
  }

  def close(): Unit = platform.close()
}

object CumulocityClient extends Log {
  val conf = CumuConfReader.load
  val client = new CumulocityClient(conf)
  val name = "play client"
  val interval = Observable.interval(60.seconds)
  var sub: Option[Subscription] = None

  def init() = {
    client.registerDevice(name)
    interval.foreach(_ => client.updateDevice(name))
  }

  def close(): Unit = client.close()
}