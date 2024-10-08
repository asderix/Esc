ThisBuild / description := "ESC is an easy to use entity similarity checker library with a powerful name matching algorithm."
ThisBuild / licenses := Seq("Apache 2.0" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / organization := "R&F Works GmbH"
ThisBuild / organizationHomepage := Some(url("https://www.r8f.ch/"))
ThisBuild / homepage := Some(url("https://esc.asderix.com/"))
ThisBuild / developers := List(
  Developer("ronnyfuchs", "Ronny Fuchs", "info@asderix.com", url("http://esc.asderix.com"))
)
ThisBuild / scalaVersion := "3.5.0"

lazy val Esc = (project in file("."))
  .settings(
    name := "EntitySimilarityChecker",    
    libraryDependencies += "com.lihaoyi" %% "upickle" % "4.0.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test",
    libraryDependencies += "org.apache.lucene" % "lucene-core" % "9.11.1",
    libraryDependencies += "org.apache.lucene" % "lucene-queryparser" % "9.11.1",
    libraryDependencies += "org.apache.lucene" % "lucene-analysis-common" % "9.11.1"
  )

assembly / assemblyMergeStrategy := {
  case "module-info.class" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}

assembly / assemblyJarName := "EscEntitySimilarityChecker_2.4.0.jar"