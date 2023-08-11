package world.factors.expression.impl;

import context.Context;
import world.factors.expression.api.AbstractExpression;
import world.factors.function.api.Function;

public class UtilFunctionExpression extends AbstractExpression {
    public UtilFunctionExpression(String expression, Context context) {
        super(expression, context);
    }

    @Override
    public Function evaluate(Context context) {
        return context.getFunctionByExpression(expression);
    }
}
