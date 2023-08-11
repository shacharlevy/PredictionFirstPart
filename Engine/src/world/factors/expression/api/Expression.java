package world.factors.expression.api;

import context.Context;

public interface Expression {
    ExpressionType getExpressionType();
    String getStringExpression();
    Object evaluate(Context context);
}