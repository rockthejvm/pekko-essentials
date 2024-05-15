name := "pekko-essentials-typed"

version := "0.1"

scalaVersion := "3.3.3"

val pekkoVersion = "1.0.1"
val scalaTestVersion = "3.2.9"
val logbackVersion = "1.2.10"

libraryDependencies ++= Seq(
  "org.apache.pekko" %% "pekko-actor-typed" % pekkoVersion,
  "org.apache.pekko" %% "pekko-actor-testkit-typed" % pekkoVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
)
