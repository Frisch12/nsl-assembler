package nsl.instruction;

import nsl.*;
import nsl.expression.AssembleExpression;
import nsl.expression.Expression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;

public class AddIncludeDirInstruction extends AssembleExpression {
    public static final String name = "AddIncludeDir";

    private Expression path;

    @SuppressWarnings("Duplicates")
    public AddIncludeDirInstruction(int returns) {
        if(!ScriptParser.inGlobalContext())
            throw new NslContextException(EnumSet.of(NslContext.Global), name);
        if(returns > 0)
            throw new NslReturnValueException(name);

        ArrayList<Expression> paramList = Expression.matchList();
        if(paramList.size() != 1)
            throw new NslArgumentException(name, 1);

        this.path = paramList.get(0);
    }

    @Override
    public void assemble() throws IOException {
        AssembleExpression.assembleIfRequired(this.path);

        ScriptParser.writeLine("!addincludedir \"" + this.path.getStringValue() + "\"");
    }

    @Override
    public void assemble(Register var) throws IOException {
        throw new UnsupportedOperationException("Not supported.");
    }
}
