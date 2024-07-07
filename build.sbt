ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "monkey"
  )

libraryDependencies += "org.jline" % "jline" % "3.26.2"

// Test dependencies
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % Test