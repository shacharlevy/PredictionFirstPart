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
        PropertyInstance propertyInstance = context.getPropertyByName(this.entityDefinition.getName());


        switch (this.operator){
            case EQUALS:

                break;
            case NOT_EQUALS:
                break;
            case BIGGER_THAN:
                break;
            case LOWER_THAN:
                break;
            default:

        }
    }
}
