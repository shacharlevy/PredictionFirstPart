package world.factors.action.impl;

import world.factors.action.api.AbstractAction;
import world.factors.action.api.ActionType;

import world.factors.entity.definition.EntityDefinition;
import world.factors.property.definition.api.PropertyType;
import context.Context;
import world.factors.property.execution.PropertyInstance;

public class IncreaseAction extends AbstractAction {

    private final String property;
    private final String byExpression;

    public IncreaseAction(EntityDefinition entityDefinition, String property, String byExpression) {
        super(ActionType.INCREASE, entityDefinition);
        this.property = property;
        this.byExpression = byExpression;
    }

    @Override
    public void invoke(Context context) {
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);

        if (verifyDecimalPropertyType(propertyInstance)) {
            Integer v = PropertyType.DECIMAL.convert(propertyInstance.getValue());

            propertyInstance.updateValue(v + this.byExpression);
            return;
        }

        else if (verifyFloatPropertyType(propertyInstance)) {
            Float v = PropertyType.FLOAT.convert(propertyInstance.getValue());
            propertyInstance.updateValue(v + this.byExpression);
            return;
        }

        else {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property + "]");
        }




        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property + "]");
        }
        if (PropertyType.DECIMAL.equals(propertyInstance.getPropertyDefinition().getType())) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property + "]");
        }
        Integer v = PropertyType.DECIMAL.convert(propertyInstance.getValue());

        // updating result on the property
        propertyInstance.updateValue(v + this.byExpression);
    }

    private boolean verifyDecimalPropertyType(PropertyInstance propertyValue) {
        return PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType());
    }

    private boolean verifyFloatPropertyType(PropertyInstance propertyValue) {
        return PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }
}
