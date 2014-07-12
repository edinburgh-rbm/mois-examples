name := "examples-module"

organization := "org.example"

version := "1.99.3-SNAPSHOT"

scalaVersion := "2.11.1"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "uk.ac.ed.inf" %% "mois" % "1.99.3-SNAPSHOT"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.0" % "test"
