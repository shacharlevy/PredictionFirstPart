package world.factors.expression.api;

import context.Context;
import world.factors.entity.definition.EntityDefinition;
import world.factors.function.api.FunctionType;

public interface Expression {
    ExpressionType getExpressionType();
    String getStringExpression();
    Object evaluate(Context context);
    boolean isNumericExpression();
    FunctionType getFunctionTypeByExpression(String expression);
}