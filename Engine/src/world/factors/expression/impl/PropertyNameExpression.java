package world.factors.expression.impl;

import context.Context;
import world.factors.entity.definition.EntityDefinition;
import world.factors.expression.api.AbstractExpression;
import world.factors.expression.api.ExpressionType;
import world.factors.property.definition.api.EntityPropertyDefinition;
import world.factors.property.definition.api.PropertyType;
import world.factors.property.execution.PropertyInstance;

import java.util.List;

public class PropertyNameExpression extends AbstractExpression {
    public PropertyNameExpression(String expression) {
        super(expression, ExpressionType.PROPERTY_NAME);
    }

    @Override
    public PropertyInstance evaluate(Context context) {
        return context.getPropertyByName(expression);
    }

    @Override
    public boolean isNumericExpression(List<EntityDefinition> entityDefinitions) {
        // check if the property type of the property of the entity is numeric
        for (EntityDefinition entityDefinition : entityDefinitions) {
            EntityPropertyDefinition entityPropertyDefinition = entityDefinition.getPropertyDefinitionByName(expression);
            if (entityPropertyDefinition.getType() == PropertyType.FLOAT || entityPropertyDefinition.getType() == PropertyType.DECIMAL) {
                return true;
            }
        }
        return false;
    }
}
