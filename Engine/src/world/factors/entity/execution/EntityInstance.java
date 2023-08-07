package world.factors.entity.execution;

import world.factors.property.execution.PropertyInstance;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
}
