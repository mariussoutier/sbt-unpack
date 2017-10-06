name := "sbt-unpack"
organization in ThisBuild := "com.mariussoutier.sbt"

description := ""
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

scalacOptions += "-deprecation"

sbtPlugin := true
crossSbtVersions := Seq("0.13.16", "1.0.2")

publishMavenStyle := false
//bintrayOrganization := Some("sbt")
bintrayOrganization in bintray := None
bintrayRepository := "sbt-plugins"
//bintrayPackage := "sbt-release"
//bintrayReleaseOnPublish := false
