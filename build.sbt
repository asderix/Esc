ThisBuild / description := "ESC is an easy to us entity similarity checker library with a powerful name matching algorithm."
ThisBuild / licenses := Seq("Apache 2.0" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / organization := "R&F Works GmbH"
ThisBuild / organizationHomepage := Some(url("https://www.r8f.ch/"))
ThisBuild / homepage := Some(url("https://esc.asderix.com/"))
ThisBuild / developers := List(
  Developer("ronnyfuchs", "Ronny Fuchs", "info@asderix.com", url("http://esc.asderix.com"))
)
ThisBuild / scalaVersion := "2.13.8"

lazy val Esc = (project in file("."))
  .settings(
    name := "EntitySimilarityChecker",    
    libraryDependencies += "io.spray" %%  "spray-json" % "1.3.6",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-M1" % "test",
    libraryDependencies += "org.apache.lucene" % "lucene-core" % "8.11.1",
    libraryDependencies += "org.apache.lucene" % "lucene-queryparser" % "8.11.1",
    libraryDependencies += "org.apache.lucene" % "lucene-analyzers-common" % "8.11.1"
  )
