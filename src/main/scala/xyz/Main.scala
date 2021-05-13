package xyz

import org.antlr.v4.runtime.atn.PredictionMode
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.apache.spark.sql.catalyst.parser.{ParseErrorListener, PostProcessor}


object Main {
  def parse(command: String): SampleParser = {
    val lexer = new SampleLexer(
      new UpperCaseCharStream(CharStreams.fromString(command))
    )
    lexer.removeErrorListeners()
    lexer.addErrorListener(ParseErrorListener)

    val tokenStream = new CommonTokenStream(lexer)
    val parser = new SampleParser(tokenStream)
    parser.addParseListener(PostProcessor)
    parser.removeErrorListeners()
    parser.addErrorListener(ParseErrorListener)
    parser.getInterpreter.setPredictionMode(PredictionMode.SLL)
    parser
  }

  def main(args: Array[String]): Unit = {
    val parser = parse(
      """
        |model options 10.0;
        |""".stripMargin)
    val astBuilder = new SampleAstBuilder
    astBuilder.visit(parser.singleStatement())
  }
}
