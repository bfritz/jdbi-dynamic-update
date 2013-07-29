name := "jdbi-dynamic-update"

organization := "com.bfritz.example"

version := "0.1.0-SNAPSHOT"

homepage := Some(url("https://github.com/bfritz/jdbi-dynamic-update"))

startYear := Some(2013)

scmInfo := Some(
  ScmInfo(
    url("https://github.com/bfritz/jdbi-dynamic-update"),
    "scm:git:https://github.com/bfritz/jdbi-dynamic-update.git",
    Some("scm:git:git@github.com:bfritz/jdbi-dynamic-update.git")
  )
)

/* scala versions and options */
scalaVersion := "2.10.2"

crossScalaVersions := Seq(
/*  "2.9.3-RC1",
  "2.9.2",
  "2.9.1", "2.9.1-1",
  "2.9.0", "2.9.0-1",
  "2.8.0", "2.8.1", "2.8.2" */
)

// These options will be used for *all* versions.
scalacOptions ++= Seq(
  "-deprecation"
  ,"-unchecked"
  ,"-encoding", "UTF-8"
  ,"-target:jvm-1.6"
  // "-optimise"   // this option will slow your build
)

scalacOptions ++= Seq(
  "-Yclosure-elim",
  "-Yinline"
)

// These language flags will be used only for 2.10.x.
// Uncomment those you need, or if you hate SIP-18, all of them.
scalacOptions <++= scalaVersion map { sv =>
  if (sv startsWith "2.10") List(
    "-Xverify"
    ,"-Ywarn-all"
    ,"-feature"
    ,"-language:postfixOps"
    // "-language:reflectiveCalls",
    // "-language:implicitConversions"
    // "-language:higherKinds",
    // "-language:existentials",
    // "-language:experimental.macros",
    // "-language:experimental.dynamics"
  )
  else Nil
}

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

/* dependencies */
libraryDependencies ++= Seq (
  "com.h2database" % "h2" % "1.3.172"
  ,"org.jdbi" % "jdbi" % "2.49"
  ,"ch.qos.logback" % "logback-classic" % "1.0.13"
  ,"com.jsuereth" %% "scala-arm" % "1.3"
  ,"org.slf4j" % "slf4j-api" % "1.7.5"
)

/* you may need these repos */
resolvers ++= Seq(
  // Resolver.sonatypeRepo("snapshots")
  // Resolver.typesafeRepo("releases")
  // "spray repo" at "http://repo.spray.io"
)

/* assembly plugin */
mainClass in AssemblyKeys.assembly := Some("com.mlh.jDBI_Dynamic_Update.Main")

assemblySettings

test in AssemblyKeys.assembly := {}
