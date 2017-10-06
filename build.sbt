name := "sbt-unpack"
organization in ThisBuild := "com.mariussoutier.sbt"

description := ""
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

scalacOptions += "-deprecation"

sbtPlugin := true
crossSbtVersions := Seq("0.13.16", "1.0.2")
scalaVersion := (CrossVersion partialVersion (sbtVersion in pluginCrossBuild).value match {
  case Some((0, 13)) => "2.10.6"
  case Some((1, _))  => "2.12.2"
  case _             => sys error s"Unhandled sbt version ${(sbtVersion in pluginCrossBuild).value}"
})

publishMavenStyle := false
bintrayOrganization in bintray := None
bintrayRepository := "sbt-plugins"

releaseCrossBuild := true
