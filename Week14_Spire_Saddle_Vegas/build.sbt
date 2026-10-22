// Sl. No. 25 - Spire + Saddle + Vegas (Saddle and Vegas are published only for Scala 2.11, so a Java 8 JDK is required)
name := "week14-spire-saddle-vegas"
version := "1.0"
scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.typelevel"    %% "spire"       % "0.14.1", // 0.15+ pulls a cats-kernel that clashes with Vegas
  "org.scala-saddle" %% "saddle-core" % "1.3.4",
  "org.vegas-viz"    %% "vegas"       % "0.3.11"
)

run / fork := true
run / baseDirectory := (ThisBuild / baseDirectory).value
