package world.factors.property.execution;

import world.factors.property.definition.api.PropertyDefinition;

public interface PropertyInstance {
    PropertyDefinition getPropertyDefinition();
    Object getValue();
    void updateValue(Object val);

}
