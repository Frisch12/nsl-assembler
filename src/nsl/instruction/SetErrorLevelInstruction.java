/*
 * SetErrorLevelInstruction.java
 */

package nsl.instruction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import nsl.*;
import nsl.expression.*;

/**
 * @author Stuart
 */
public class SetErrorLevelInstruction extends AssembleExpression
{
  public static final String name = "SetErrorLevel";
  private final Expression errorLevel;

  /**
   * Class constructor.
   * @param returns the number of values to return
   */
  public SetErrorLevelInstruction(int returns)
  {
    if (!SectionInfo.in() && !FunctionInfo.in())
      throw new NslContextException(EnumSet.of(NslContext.Section, NslContext.Function), name);
    if (returns > 0)
      throw new NslReturnValueException(name);

    ArrayList<Expression> paramsList = Expression.matchList();
    if (paramsList.size() != 1)
      throw new NslArgumentException(name, 1);

    this.errorLevel = paramsList.get(0);
  }

  /**
   * Assembles the source code.
   */
  @Override
  public void assemble() throws IOException
  {
    Expression varOrErrorLevel = AssembleExpression.getRegisterOrExpression(this.errorLevel);
    ScriptParser.writeLine(name + " " + varOrErrorLevel);
    varOrErrorLevel.setInUse(false);
  }

  /**
   * Assembles the source code.
   * @param var the variable to assign the value to
   */
  @Override
  public void assemble(Register var) throws IOException
  {
    throw new UnsupportedOperationException("Not supported.");
  }
}
