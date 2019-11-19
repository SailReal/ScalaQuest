name := "ScalaTest"
version := "0.1"
scalaVersion := "2.13.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

libraryDependencies += "org.scalafx" %% "scalafx" % "12.0.2-R18"
libraryDependencies += "com.google.inject" % "guice" % "4.2.2"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.6"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.0-M7"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add dependency on JavaFX libraries, OS dependent
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m =>
  "org.openjfx" % s"javafx-$m" % "12.0.2" classifier osName
)