name := "ServiceTrackAPI"
 
version := "1.0" 
      
lazy val `servicetrackapi` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , 
  ehcache , 
  ws , 
  specs2 % Test , 
  guice,
  evolutions
)

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % "test"
)
libraryDependencies += "org.playframework.anorm" %% "anorm" % "2.6.2"

libraryDependencies += "io.swagger" %% "swagger-play2" % "1.7.0"
libraryDependencies += "org.webjars" %% "webjars-play" % "2.7.0"
libraryDependencies += "org.webjars" % "swagger-ui" % "3.22.0"


libraryDependencies += "com.h2database" % "h2" % "1.4.192"
unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      