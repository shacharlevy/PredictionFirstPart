package world.factors.expression.impl;

import context.Context;
import world.factors.expression.api.AbstractExpression;

public class FreeValueExpression extends AbstractExpression {
    public FreeValueExpression(String expression, Context context) {
        super(expression, context);
    }

    @Override
    public Object evaluate(Context context) {
        // if the expression is an integer, return it as an integer
         if (expression.matches("-?\\d+")) {
            return Integer.parseInt(expression);
        }
        // if the expression is a number, return it as a float
        else if (expression.matches("-?\\d+(\\.\\d+)?")) {
            return Float.parseFloat(expression);
        }
        // if the expression is a boolean, return it as a boolean
        else if (expression.matches("true|false")) {
            return Boolean.parseBoolean(expression);
        } else {
            return expression;
        }
    }
}
