libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.1.1"
)

antlr4PackageName in Antlr4 := Some("xyz")
antlr4GenVisitor in Antlr4 := true

enablePlugins(Antlr4Plugin)
