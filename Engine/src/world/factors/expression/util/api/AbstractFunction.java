package world.factors.expression.util.api;

import world.factors.environment.EnvProperty;

import java.util.ArrayList;

public abstract class AbstractFunction implements Function{
    private final FunctionType functionType;
    private final ArrayList<String> argumentTypes;
    private final int numArgs;

    public AbstractFunction(FunctionType functionType, ArrayList<String> argumentTypes, int numArgs) {
        this.functionType = functionType;
        this.argumentTypes = argumentTypes;
        this.numArgs = numArgs;
    }

    @Override
    public FunctionType getFunctionType() {
        return this.functionType;
    }
}
