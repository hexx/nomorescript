import sbt._, Keys._

object Build extends Build {
  lazy val baseSettings = Seq(
    scalaVersion := "2.10.1",
    version := "0.1.0-SNAPSHOT",
    organization := "com.github.suzuki0keiichi",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:_"),
    libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _)
  )

  lazy val root = Project(
    id = "nomorescript",
    base = file(".")
  ).settings(
    baseSettings:_*
  ).aggregate(plugin, browser, jquery, enchantjs)

  lazy val plugin = Project(
    id = "nomorescript-plugin",
    base = file("nomorescript-plugin")
  ).settings(
    baseSettings ++ seq(
      name := "nomorescript-plugin"
    ) : _*
  )

  lazy val browser = Project(
    id = "nomorescript-option-browser",
    base = file("nomorescript-option-browser")
  ).settings(
    baseSettings ++ seq(
      name := "nomorescript-option-browser"
    ) : _*
  ).dependsOn(plugin)

  lazy val jquery = Project(
    id = "nomorescript-option-jquery",
    base = file("nomorescript-option-jquery")
  ).settings(
    baseSettings ++ seq(
      name := "nomorescript-option-jquery"
    ) : _*
  ).dependsOn(plugin)

  lazy val enchantjs = Project(
    id = "nomorescript-option-enchantjs",
    base = file("nomorescript-option-enchantjs")
  ).settings(
    baseSettings ++ seq(
      name := "nomorescript-option-enchantjs"
    ) : _*
  ).dependsOn(plugin)
}
