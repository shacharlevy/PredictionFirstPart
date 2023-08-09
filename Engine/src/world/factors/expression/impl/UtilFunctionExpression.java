package world.factors.expression.impl;

import context.Context;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.ExpressionType;
import world.factors.function.api.Function;
import world.factors.function.api.FunctionType;

import java.util.List;

public class UtilFunctionExpression extends AbstractExpression {
    private Function function;

    public UtilFunctionExpression(String expression, Function function, Context context) {
        super(expression, context);
        this.function = function;
    }
}
