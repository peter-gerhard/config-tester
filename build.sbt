import com.typesafe.sbt.packager.docker.Cmd

name := "config-experiment"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "com.typesafe" % "config" % "1.3.2"

lazy val projectd = (project in file("."))
    .settings(settings).enablePlugins(DockerPlugin)

lazy val settings = dockerSettings

lazy val dockerSettings = Seq(
  dockerBaseImage := "frolvlad/alpine-oraclejdk8:8.181.13-slim",
  packageName in Docker := "config-tester",
  version in Docker := version.value,
  dockerRepository := Some("gcr.io/commercetools-platform"),
  dockerUpdateLatest := false,
  dockerExposedVolumes := Seq(s"/etc/config-tester/"),
  dockerCommands :=
    Seq(
      dockerCommands.value.head,
      Cmd("RUN apk add --update bash && rm -rf /var/cache/apk/*")) ++
      dockerCommands.value.tail,
  dockerCommands += Cmd("ADD", s"etc/config-tester /etc/config-tester"))

//val processJvmSettings = Seq(
//  packageJvmOpts := Seq("-server", "-Dconfig.file=config/environment.conf"))
