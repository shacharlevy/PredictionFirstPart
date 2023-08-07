package world.factors.expression.impl;

import world.factors.expression.api.Expression;

public class ExpressionImpl implements Expression {
    private String name;
    private String expression;

    public ExpressionImpl(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setExpression(String expression) {
        this.expression = expression;
    }
}
