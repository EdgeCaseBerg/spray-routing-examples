organization := "example"

name := "routing-confusion"

version := "0.0.0-SNAPSHOT" 

scalaVersion := "2.10.5"

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _)

libraryDependencies ++= { 
	val sprayV = "1.3.3" 
	val akkaV = "2.3.9"
	val anormVersion = "2.3.+"
	Seq(
		"io.spray"            %%  "spray-can"     % sprayV,
		"io.spray"            %%  "spray-routing" % sprayV,
		"io.spray"            %%  "spray-json"    % "1.3.1",
		"com.typesafe.akka"   %%  "akka-actor"    % akkaV,
		"com.typesafe" % "config" % "1.2.1",
		"com.wix" %% "accord-core" % "0.4.2",
		"com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
		"io.spray"            %%  "spray-testkit" % sprayV  % "test",
		"org.scalatest" % "scalatest_2.10" % "2.2.4" % "test"
	)
}


Revolver.settings