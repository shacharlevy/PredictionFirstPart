package world.factors.expression.api;

import context.Context;

public abstract class AbstractExpression implements Expression {
    protected final ExpressionType expressionType;
    protected final String expression;

    protected AbstractExpression(String expression, Context context) {
        this.expression = expression;
        this.expressionType = context.getExpressionType(expression);
    }

    @Override
    public ExpressionType getExpressionType() {
        return expressionType;
    }

    @Override
    public String getStringExpression() {
        return expression;
    }
}