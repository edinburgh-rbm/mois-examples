/*
 *  MOIS-Examples: SBT Build Instructions
 *  Copyright (C) 2014 University of Edinburgh School of Informatics
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
name := "mois-examples"

organization := "uk.ac.ed.inf"

version := "1.99.17-SNAPSHOT"

scalaVersion := "2.11.2"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "ucar-unidata-releases" at "https://artifacts.unidata.ucar.edu/content/repositories/unidata-releases/"

libraryDependencies += "uk.ac.ed.inf" %% "mois" % "1.99.17-SNAPSHOT" exclude("xml-apis", "xml-apis")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.0" % "test"

libraryDependencies += "xml-apis" % "xml-apis" % "1.4.01"

mainClass in Compile := Some("uk.ac.ed.inf.mois.MoisMain")

// Everything below this line is for publishing models/software to
// public repositories. It -- or something like it -- is necessary
// for easy sharing and reuse of models.

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := (
  <url>https://gallows.inf.ed.ac.uk/rbm/mois</url>
  <licenses>
    <license>
      <name>GPLv3 or Later</name>
      <url>https://www.gnu.org/licenses/gpl-3.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:edinburgh-rbm/mois.git</url>
    <connection>scm:git:git@github.com:edinburgh-rbm/mois-examples.git</connection>
  </scm>
  <developers>
    <developer>
      <id>dominik</id>
      <name>Dominik Bucher</name>
    </developer>
    <developer>
      <id>rhz</id>
      <name>Ricardo Honorato Z</name>
    </developer>
    <developer>
      <id>ww</id>
      <name>William Waites</name>
    </developer>
  </developers>
)

useGpg := true

usePgpKeyHex("84225CBC")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
