package nsl.instruction;

import nsl.*;
import nsl.expression.AssembleExpression;
import nsl.expression.Expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;

public class IncludeInstruction extends AssembleExpression {
    public static final String name = "Include";

    private Expression filename;

    @SuppressWarnings("Duplicates")
    public IncludeInstruction(int returns) {
        if(!ScriptParser.inGlobalContext())
            throw new NslContextException(EnumSet.of(NslContext.Global), name);
        if(returns > 0)
            throw new NslReturnValueException(name);

        ArrayList<Expression> paramList = Expression.matchList();
        if(paramList.size() != 1)
            throw new NslArgumentException(name, 1);

        this.filename = paramList.get(0);
    }

    @Override
    public void assemble() throws IOException {
        AssembleExpression.assembleIfRequired(this.filename);

        ScriptParser.writeLine("!include \"" + this.filename.getStringValue() + "\"");

    }

    @Override
    public void assemble(Register var) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }
}
