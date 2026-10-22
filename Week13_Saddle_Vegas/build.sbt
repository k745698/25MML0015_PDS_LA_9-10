// Sl. No. 23 - Saddle + Vegas (both libraries are published only for Scala 2.11, so a Java 8 JDK is required)
name := "week13-saddle-vegas"
version := "1.0"
scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.scala-saddle" %% "saddle-core" % "1.3.4",
  "org.vegas-viz"    %% "vegas"       % "0.3.11"
)

run / fork := true
run / baseDirectory := (ThisBuild / baseDirectory).value
