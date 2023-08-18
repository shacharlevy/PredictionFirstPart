package world.factors.action.impl;

import world.factors.action.api.AbstractAction;
import world.factors.action.api.ActionType;

import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.expression.api.Expression;
import world.factors.property.definition.api.PropertyType;
import context.Context;
import world.factors.property.execution.PropertyInstance;

import java.util.List;

import static world.factors.expression.api.AbstractExpression.getExpressionByString;

public class DecreaseAction extends AbstractAction {

    private final String property;
    private final String byExpression;

    public DecreaseAction(EntityDefinition entityDefinition, String property, String byExpression) {
        super(ActionType.DECREASE, entityDefinition);
        this.property = property;
        this.byExpression = byExpression;
    }

    public String getProperty() {
        return property;
    }

    public String getByExpression() {
        return byExpression;
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        Expression expression = getExpressionByString(byExpression, context.getPrimaryEntityInstance().getEntityDefinition());
        Object value = context.getValueByExpression(expression);
        if (propertyInstance.getType() == PropertyType.DECIMAL) {
            /*forum question:
             * As part of calculation operations calculation \ increase \ decrease
             * what do we do in case the result of the operation is real and the PropertyType is an integer?
             * Answer:
             * In this case, of course, it is not possible to insert a real number into an integer, so it will be considered an error.
             * But if it was the other way around (for example an increase operation with a by of 3 for a property that is real)
             *  - of course it makes sense to allow since a real number can deal with an integer...*/
            Integer v = PropertyType.DECIMAL.convert(propertyInstance.getValue());
            // if the string byExpression is a real number (for example 3.5) then throw an exception
            if (value instanceof Float) {
                throw new IllegalArgumentException("decrease action of a real number can't operate on an integer property [" + property + "]");
            } else if (value instanceof Integer) {
                propertyInstance.updateValue(v - (int)value);
            }
        }

        else if (propertyInstance.getType() == PropertyType.FLOAT) {
            Float v = PropertyType.FLOAT.convert(propertyInstance.getValue());
            if (value instanceof Float) {
                propertyInstance.updateValue(v - (float) value);
            }
        }

        else {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property + "]");
        }
    }

    @Override
    public boolean isPropertyExistInEntity() {
        return entityDefinition.getPropertyDefinitionByName(property) != null;
    }

    public boolean isMathActionHasNumericArgs(List<EntityDefinition> entities, EnvVariableManagerImpl envVariableManager) {
        Expression expression = getExpressionByString(byExpression, entityDefinition);
        return expression.isNumericExpression(entities, envVariableManager);
    }
}
