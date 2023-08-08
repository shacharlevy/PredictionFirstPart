package world.factors.function.api;

import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.Expression;

import java.util.ArrayList;

public abstract class AbstractFunction implements Function {
    private final FunctionType functionType;
    private final ArrayList<Expression> expressions;
    private final int numArgs;

    public AbstractFunction(FunctionType functionType, ArrayList<Expression> expressions, int numArgs) {
        this.functionType = functionType;
        this.expressions = expressions;
        this.numArgs = numArgs;
    }

    @Override
    public FunctionType getFunctionType() {
        return this.functionType;
    }

    @Override
    public int getNumArguments() {
        return this.numArgs;
    }
}
