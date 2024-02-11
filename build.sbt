ThisBuild / description := "ESC is an easy to use entity similarity checker library with a powerful name matching algorithm."
ThisBuild / licenses := Seq("Apache 2.0" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / organization := "R&F Works GmbH"
ThisBuild / organizationHomepage := Some(url("https://www.r8f.ch/"))
ThisBuild / homepage := Some(url("https://esc.asderix.com/"))
ThisBuild / developers := List(
  Developer("ronnyfuchs", "Ronny Fuchs", "info@asderix.com", url("http://esc.asderix.com"))
)
ThisBuild / scalaVersion := "3.3.1"

lazy val Esc = (project in file("."))
  .settings(
    name := "EntitySimilarityChecker",    
    libraryDependencies += "com.lihaoyi" %% "upickle" % "3.1.4",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test",
    libraryDependencies += "org.apache.lucene" % "lucene-core" % "9.9.1",
    libraryDependencies += "org.apache.lucene" % "lucene-queryparser" % "9.9.1",
    libraryDependencies += "org.apache.lucene" % "lucene-analysis-common" % "9.9.1"
  )

assemblyMergeStrategy in assembly := {
  case "module-info.class" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}