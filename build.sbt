ThisBuild / description := "ESC is an easy to us entity similarity checker library with a powerful name matching algorithm."
ThisBuild / licenses := Seq("Apache 2.0" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / organization := "R&F works"
ThisBuild / organizationHomepage := Some(url("http://asderix.com/"))
ThisBuild / homepage := Some(url("http://esc.asderix.com/"))
ThisBuild / developers := List(
  Developer("ronnyfuchs", "Ronny Fuchs", "info@asderix.com", url("http://asderix.com"))
)
ThisBuild / scalaVersion := "2.12.7"

//mainClass in (Compile, run) := Some("esc.application.EscApp")

lazy val Esc = (project in file("."))
  .settings(
    name := "EntitySimilarityChecker",    
    libraryDependencies += "io.spray" %%  "spray-json" % "1.3.5",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )
