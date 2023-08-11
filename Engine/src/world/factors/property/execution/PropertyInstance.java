package world.factors.property.execution;

import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.definition.api.PropertyType;

public interface PropertyInstance {
    PropertyDefinition getPropertyDefinition();
    Object getValue();
    void updateValue(Object val);
    PropertyType getType();
}
