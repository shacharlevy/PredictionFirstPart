package world.factors.expression.impl;

import context.Context;
import world.factors.entity.definition.EntityDefinition;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.ExpressionType;
import world.factors.function.api.Function;
import world.factors.function.api.FunctionType;

import java.util.List;

public class UtilFunctionExpression extends AbstractExpression {
    public UtilFunctionExpression(String expression) {
        super(expression, ExpressionType.UTIL_FUNCTION);
    }

    @Override
    public Function evaluate(Context context) {
        return getFunctionByExpression(expression);
    }

    @Override
    public boolean isNumericExpression(List<EntityDefinition> entityDefinitions) {
        FunctionType functionType = getFunctionTypeByExpression(expression);
        switch (functionType) {
            case ENVIRONMENT:
                Function function = getFunctionByExpression(expression);
                return function.isNumericFunction();
            case RANDOM:
                return true;
            default:
                return false;
        }
    }
}
