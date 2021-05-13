package xyz

import org.antlr.v4.runtime.tree.ParseTree
import org.apache.spark.sql.catalyst.parser.ParserUtils.{string, withOrigin}
import xyz.SampleParser.{OptionContext, OptionListContext, QualifiedNameContext, SingleStatementContext}

import scala.collection.JavaConverters.asScalaBufferConverter


class SampleAstBuilder extends SampleBaseVisitor[AnyRef] {
  override def visitSingleStatement(ctx: SingleStatementContext): String = {
    println("==> visitSingleStatement")
    withOrigin(ctx) {
      visit(ctx.statement)
    }
    ""
  }

  override def visitOptionList(ctx: OptionListContext): Map[String, String] =
    ctx match {
      case null => Map.empty
      case _    => ctx.option().asScala.map(visitOption).toMap
    }

  override def visitOption(ctx: OptionContext): (String, String) = {
    println("==> visitOption")
    // TODO: find a more scala way?
    val value: String = if (ctx.value.booleanValue() != null) {
      if (ctx.value.booleanValue().TRUE() != null)
        "true"
      else {
        "false"
      }
    } else if (ctx.value.FLOATING_VALUE() != null) {
      println(ctx.value.getText)
      ctx.value.FLOATING_VALUE.getSymbol.getText
    } else if (ctx.value.INTEGER_VALUE() != null) {
      println(ctx.value.getText)
      ctx.value.INTEGER_VALUE.getSymbol.getText
    } else {
      string(ctx.value.STRING)
    }

    visitQualifiedName(ctx.key.qualifiedName()) -> value
  }

  override def visitQualifiedName(ctx: QualifiedNameContext): String =
    ctx.getText

  override def visitDebugModel(ctx: SampleParser.DebugModelContext): AnyRef = {
    println("==> visitDebugModel")
    visitOptionList(ctx.optionList())
  }

  /**
   * Visit a parse tree produced by the {@code unquotedIdentifier}
   * labeled alternative in {@link SampleParser#   identifier}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  override def visitUnquotedIdentifier(ctx: SampleParser.UnquotedIdentifierContext): AnyRef = ???

  /**
   * Visit a parse tree produced by the {@code quotedIdentifierAlternative}
   * labeled alternative in {@link SampleParser# identifier}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  override def visitQuotedIdentifierAlternative(ctx: SampleParser.QuotedIdentifierAlternativeContext): AnyRef = ???

  /**
   * Visit a parse tree produced by {@link SampleParser# quotedIdentifier}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  override def visitQuotedIdentifier(ctx: SampleParser.QuotedIdentifierContext): AnyRef = ???

  /**
   * Visit a parse tree produced by {@link SampleParser# nonReserved}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  override def visitNonReserved(ctx: SampleParser.NonReservedContext): AnyRef = ???

  /**
   * Visit a parse tree produced by {@link SampleParser# optionKey}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  override def visitOptionKey(ctx: SampleParser.OptionKeyContext): AnyRef = ???

  /**
   * Visit a parse tree produced by {@link SampleParser# optionValue}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  override def visitOptionValue(ctx: SampleParser.OptionValueContext): AnyRef = ???

  /**
   * Visit a parse tree produced by {@link SampleParser# booleanValue}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  override def visitBooleanValue(ctx: SampleParser.BooleanValueContext): AnyRef = ???
}
