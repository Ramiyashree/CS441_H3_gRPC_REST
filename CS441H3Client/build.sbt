name := "CS441H3Client"

name := "gRPC-REST-CS441-H3"

version := "0.1"

scalaVersion := "2.13.6"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies ++=Seq(
  "com.typesafe" % "config" % "1.3.2",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.gnieh" % "logback-config" % "0.3.1",
  "org.scalactic" %% "scalactic" % "3.2.9",
  "org.scalatest" %% "scalatest" % "3.2.9" % "test",
  "org.scalatest" %% "scalatest-featurespec" % "3.2.9" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "com.amazonaws" % "aws-lambda-java-core" % "1.2.0",
  "com.amazonaws" % "aws-lambda-java-events" % "2.2.6",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.12.90",
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "com.google.code.gson" % "gson" % "2.8.8"
)


Compile / PB.targets := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)



