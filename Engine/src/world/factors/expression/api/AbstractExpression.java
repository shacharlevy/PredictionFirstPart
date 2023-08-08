package world.factors.expression.api;

import context.Context;

public abstract class AbstractExpression implements Expression {
    private final String expression;
    private final ExpressionType expressionType;

    protected AbstractExpression(String expression, ExpressionType expressionType, Context context) {
        this.expression = expression;
        this.expressionType = context.getExpressionType(expression);
    }

    @Override
    public ExpressionType getExpressionType() {
        return expressionType;
    }
}
