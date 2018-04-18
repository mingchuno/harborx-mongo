import Keys._
import xerial.sbt.Sonatype._

Project.inConfig(Test)(sbtprotoc.ProtocPlugin.protobufConfigSettings)

lazy val commonSettings = Seq(
  scalaVersion := "2.11.12",

  organization := "com.github.mingchuno",

  crossScalaVersions := Seq("2.11.12", "2.12.4"),

  scalacOptions := Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-encoding",
    "utf8",
    "-language:postfixOps",
    "-Yrangepos",
    "-language:implicitConversions"
  ),

  fork in Test := true,

  // protobuf
  PB.targets in Test := Seq(
    scalapb.gen(
      flatPackage = true,
      javaConversions = false,
      grpc = false,
      singleLineToProtoString = true) -> (sourceManaged in Test).value
  )
)

// root project
lazy val mongo = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    // other settings
    name := "mongopb4s",
    version := "1.0.6",
    libraryDependencies ++= Seq(
      // scalapb
      "com.thesamet.scalapb" %% "scalapb-json4s" % scalapb.compiler.Version.scalapbVersion,
      // mongo-scala-driver
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",
      // Rx
      "org.reactivestreams" % "reactive-streams" % "1.0.1",
      // scalatest
      "org.scalatest" %% "scalatest" % "3.0.2" % "test",
      // akka stream for testing
      "com.typesafe.akka" %% "akka-stream" % "2.5.4" % "test",
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.4" % "test"
    ),
    publishTo := SonatypeKeys.sonatypePublishTo.value,
    publishMavenStyle := true,
    homepage := Some(url("https://github.com/mingchuno/mongopb4s")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/mingchuno/mongopb4s"),
        "scm:git@github.com:mingchuno/mongopb4s.git"
      )
    ),
    developers := List(
      Developer(id = "mingchuno", name = "Or Ming Chun", email = "mingchuno@gmail.com", url = url("https://github.com/mingchuno"))
    ),
    licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))
  )
