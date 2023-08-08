package world.factors.expression.impl;

import context.Context;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.ExpressionType;
import world.factors.function.api.FunctionType;

public class UtilFunctionExpression extends AbstractExpression {
    private FunctionType function;

    public UtilFunctionExpression(String expression, ExpressionType expressionType, Context context) {
        super(expression, expressionType, context);
        this.function = context.getFunctionType(expression);
    }

}
