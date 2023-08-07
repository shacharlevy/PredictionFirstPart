package world.factors.expression.util.impl;

import world.factors.expression.util.api.AbstractFunction;
import world.factors.expression.util.api.FunctionType;

import java.util.ArrayList;

public class EnvironmentFunction extends AbstractFunction {
    public EnvironmentFunction(FunctionType functionType, ArrayList<Object> expressions) {
        super(FunctionType.ENVIRONMENT, expressions, expressions.size());
        this.expressions = expressions;
    }
}
