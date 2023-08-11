package world.factors.expression.impl;

import context.Context;
import world.factors.expression.api.AbstractExpression;
import world.factors.property.execution.PropertyInstance;

public class PropertyNameExpression extends AbstractExpression {
    public PropertyNameExpression(String expression, Context context) {
        super(expression, context);
    }

    @Override
    public PropertyInstance evaluate(Context context) {
        return context.getPropertyByName(expression);
    }
}
