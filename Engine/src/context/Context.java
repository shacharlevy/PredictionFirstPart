package context;

import world.factors.entity.execution.EntityInstance;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.expression.api.ExpressionType;
import world.factors.function.api.Function;
import world.factors.function.api.FunctionType;
import world.factors.property.execution.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    ActiveEnvironment getEnvironment();
    ExpressionType getExpressionType(String expression);
    FunctionType getFunctionType(String expression);

    Function getFunctionByExpression(String expression);
}
