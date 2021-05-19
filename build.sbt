lazy val `servicetrackapi` = (project in file(".")).enablePlugins(PlayScala)

name := "ServiceTrackAPI"
version := "1.0"
scalaVersion := "2.13.6"

scalacOptions ++= Seq(
  "-language:postfixOps"
)
resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/",
  ("Akka Snapshot Repository" at "http://repo.akka.io/snapshots/").withAllowInsecureProtocol(true)
)

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  ws,
  specs2 % Test,
  guice,
  evolutions,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % "test",
  ("org.playframework.anorm" %% "anorm" % "2.6.10"),
  "org.webjars" %% "webjars-play" % "2.7.3",
  "com.h2database" % "h2" % "1.4.192"
)
(unmanagedResourceDirectories in Test) +=  baseDirectory.value /"target/web/public/test"

      