name := """scala_robobum"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest"      % "2.2.1" % "test",
  "junit"         %  "junit" % "4.12" % "test",
  "org.mockito"   %  "mockito-core"   % "1.9.5",
  "org.specs2"    %% "specs2-mock"    % "2.4.2",
  "io.reactivex"  %% "rxscala"        % "0.21.1"
)


