/*
 * SearchPathInstruction.java
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
public class SearchPathInstruction extends AssembleExpression
{
  public static final String name = "SearchPath";
  private final Expression file;

  /**
   * Class constructor.
   * @param returns the number of values to return
   */
  public SearchPathInstruction(int returns)
  {
    if (PageExInfo.in())
      throw new NslContextException(EnumSet.of(NslContext.Section, NslContext.Function, NslContext.Global), name);
    if (returns != 1)
      throw new NslReturnValueException(name, 1);

    ArrayList<Expression> paramsList = Expression.matchList();
    if (paramsList.size() != 1)
      throw new NslArgumentException(name, 1);

    this.file = paramsList.get(0);
  }

  /**
   * Assembles the source code.
   * @throws IOException
   */
  @Override
  public void assemble() throws IOException
  {
    throw new UnsupportedOperationException("Not supported.");
  }

  /**
   * Assembles the source code.
   * @param var the variable to assign the value to
   */
  @Override
  public void assemble(Register var) throws IOException
  {
    Expression varOrFile = AssembleExpression.getRegisterOrExpression(this.file);
    ScriptParser.writeLine(name + " " + var + " " + varOrFile);
    varOrFile.setInUse(false);
  }
}
