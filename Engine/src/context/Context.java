package context;

import world.factors.entity.execution.EntityInstance;
import world.factors.property.execution.PropertyInstance;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
}
