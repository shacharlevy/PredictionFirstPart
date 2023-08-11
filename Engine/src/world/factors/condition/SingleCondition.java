package world.factors.condition;

import context.Context;
import world.factors.entity.definition.EntityDefinition;
import world.factors.expression.api.Expression;
import world.factors.property.definition.api.EntityPropertyDefinition;
import world.factors.property.execution.PropertyInstance;

public class SingleCondition implements Condition{
    private final EntityDefinition entityDefinition;
    private final EntityPropertyDefinition propertyDefinition;
    private final OperatorType operator;
    private final Expression value;

    public SingleCondition(EntityDefinition entityDefinition, EntityPropertyDefinition propertyDefinition, OperatorType operator, Expression value)
    {
        this.entityDefinition = entityDefinition;
        this.propertyDefinition = propertyDefinition;
        this.operator = operator;
        this.value = value;
    }

    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }

    public EntityPropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    public Expression getValue() {
        return value;
    }

    public OperatorType getOperator() {
        return operator;
    }

    @Override
    public boolean assertCondition(Context context) {
        Object propertyValue = context.getPropertyByName(this.entityDefinition.getName()).getValue();
        Object value = context.getValueByExpression(this.value);
        // check if the property value is the same type as the value
        if (propertyValue.getClass() != value.getClass())
            return false;
        switch (this.operator){
            case EQUALS:
                return propertyValue.equals(value);
            case NOT_EQUALS:
                return !propertyValue.equals(value);
            case BIGGER_THAN:
                if (propertyValue instanceof Number && value instanceof Number)
                    return ((Number) propertyValue).doubleValue() > ((Number) value).doubleValue();
                break;
            case LOWER_THAN:
                if (propertyValue instanceof Number && value instanceof Number)
                    return ((Number) propertyValue).doubleValue() < ((Number) value).doubleValue();
                break;
        }
        return false;
    }
}
