import java.io.File

import com.typesafe.sbt.packager.docker.Cmd
import sbt.Keys.name

lazy val mainProject = (project in file("."))
  .settings(settings)
  .enablePlugins(JavaAppPackaging, DockerPlugin)

lazy val settings = generalSettings ++ dockerSettings

lazy val generalSettings = Seq(
  name := appName,
  version := "0.1",
  scalaVersion := "2.12.8",
  libraryDependencies += "com.typesafe" % "config" % "1.3.2",
  mainClass in Compile := Some("Main")
)

lazy val dockerSettings = Seq(
  dockerBaseImage := "frolvlad/alpine-oraclejdk8:8.181.13-slim",
  packageName in Docker := appName,
  version in Docker := version.value,
  dockerRepository := Some("gcr.io/commercetools-platform"),
  dockerUpdateLatest := false,
  dockerExposedVolumes := Seq(configFolder),
  dockerEnvVars := Map(
    "JAVA_OPTS" → s"-Dconfig.file=$configFolder/$configFile"
  ),
  dockerPackageMappings in Docker ++= Seq((new File((resourceDirectory in Compile).value, configFile), s"$configFolder/$configFile")),
  dockerCommands :=
    dockerCommands.value.head :: Cmd("RUN apk add --update bash && rm -rf /var/cache/apk/*") :: Nil ++ dockerCommands.value.tail ++ (Cmd("ADD", s"$configFolder $configFolder") :: Nil)
)


lazy val appName = "config-tester"
lazy val configFolder = s"/etc/$appName"
lazy val configFile = "application.conf"