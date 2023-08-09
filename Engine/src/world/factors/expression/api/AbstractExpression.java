package world.factors.expression.api;

import context.Context;

public abstract class AbstractExpression implements Expression {
    private final ExpressionType expressionType;

    protected AbstractExpression(String expression, Context context) {
        this.expressionType = context.getExpressionType(expression);
    }

    @Override
    public ExpressionType getExpressionType() {
        return expressionType;
    }
}
