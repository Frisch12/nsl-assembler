/*
 * DefineDirective.java
 */

package nsl.preprocessor;

import java.io.IOException;

import jdk.nashorn.internal.ir.ExpressionStatement;
import nsl.*;
import nsl.expression.*;
import nsl.statement.Statement;
import sun.font.Script;

/**
 * Defines a new preprocessor constant.
 * @author Stuart
 */
public class DefineDirective extends Statement
{
  private String name;
  private Expression value;

  /**
   * Class constructor.
   */
  public DefineDirective()
  {
    int line = ScriptParser.tokenizer.lineno();
    name = ScriptParser.tokenizer.matchAWord("a constant name");
    int valueLine = ScriptParser.tokenizer.lineno();

    // If the next token is on a new line then we can assume the constant has no
    // value associated with it.
    if (line != valueLine)
    {
      value = Expression.Empty;
    }
    else
    {
      boolean specialStringNoEscapePrevious = Expression.setSpecialStringEscape(false);
      value = Expression.matchComplex();
      Expression.setSpecialStringEscape(specialStringNoEscapePrevious);
    }

    if (!value.isLiteral() && value instanceof AssembleExpression)
      throw new NslException("\"#define\" directive (for constant \"" + name + "\") requires an expression that can be evaluated", line);

    if (!DefineList.getCurrent().add(name, value))
      throw new NslException("Constant \"" + name + "\" already defined", line);
  }

  /**
   * Assembles nothing.
   * @throws IOException
   */
  @Override
  public void assemble() throws IOException
  {
    AssembleExpression.assembleIfRequired(value);
    String stringValue = value.getStringValue();
    if(stringValue.contains(" "))
      stringValue = "\"" + stringValue +"\"";
    ScriptParser.writeLine("!define " + name + " " + stringValue);
  }
}
