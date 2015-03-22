import com.mle.sbtplay.PlayProjects
import sbt.Keys._
import sbt._

object PlayBuild extends Build {
  lazy val p = PlayProjects.plainPlayProject("iot-play").settings(commonSettings: _*)
  val cumulocityVersion = "5.23.0"

  lazy val commonSettings = cumuSettings ++ Seq(
    organization := "com.github.malliina",
    version := "0.0.1",
    scalaVersion := "2.11.6",
    retrieveManaged := false,
    fork in Test := true,
    updateOptions := updateOptions.value.withCachedResolution(true),
    libraryDependencies ++= Seq(
      "com.github.malliina" %% "play-base" % "0.2.2"),
    exportJars := true,
    javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
    scalacOptions ++= Seq(
      "-target:jvm-1.7",
      "-deprecation",
      "-encoding", "UTF-8",
      "-unchecked",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-Xfatal-warnings",
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen")
  )
  def cumuSettings = Seq(
    resolvers += "Cumulocity" at "http://download.cumulocity.com/maven/repository",
    libraryDependencies ++= Seq(
      "com.github.malliina" %% "util" % "1.6.0",
      "com.nsn.cumulocity.clients-java" % "java-client" % cumulocityVersion,
      "com.nsn.cumulocity.model" % "device-capability-model" % cumulocityVersion,
      "com.nsn.cumulocity.model" % "core-model" % cumulocityVersion,
      "com.nsn.cumulocity.model" % "rest-representation" % cumulocityVersion,
      "com.nsn.cumulocity.shared-components" % "common-rest" % cumulocityVersion notTransitive()
    )
  )
}