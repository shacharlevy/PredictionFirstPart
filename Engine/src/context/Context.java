package context;

import world.factors.entity.execution.EntityInstance;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.expression.api.Expression;
import world.factors.expression.api.ExpressionType;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.execution.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    ActiveEnvironment getEnvironment();
    ExpressionType getExpressionType(String expression);

    /*Function getFunctionByExpression(String expression);*/

    PropertyInstance getPropertyInstanceByPropertyDefinition(PropertyDefinition propertyDefinition);
    Object getValueByExpression(Expression expression);

    void setPropertyValue(String name, String property, String value);
}
