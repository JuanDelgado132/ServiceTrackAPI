lazy val `servicetrackapi` = (project in file(".")).enablePlugins(PlayScala).settings(
name := "ServiceTrackAPI",
version := "1.0",
scalaVersion := "2.13.6",
scalacOptions ++= Seq(
  "-language:postfixOps"
),
resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/",
  ("Akka Snapshot Repository" at "http://repo.akka.io/snapshots/").withAllowInsecureProtocol(true)
),
dependencyOverrides += "com.google.inject" %% "guice" % " 5.0.1",
libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  ws,
  specs2 % Test,
  guice,
  evolutions,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test",
  ("org.playframework.anorm" %% "anorm" % "2.6.10"),
  "com.h2database" % "h2" % "1.4.192"
),
Test / unmanagedResourceDirectories += baseDirectory.value /"target/web/public/test"
)

      