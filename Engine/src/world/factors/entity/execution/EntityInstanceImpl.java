package world.factors.entity.execution;

import world.factors.entity.definition.EntityDefinition;
import world.factors.property.execution.PropertyInstance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EntityInstanceImpl implements EntityInstance, Serializable {

    private final EntityDefinition entityDefinition;
    private final int id;
    private Map<String, PropertyInstance> properties;

    public EntityInstanceImpl(EntityDefinition entityDefinition, int id) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new HashMap<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public PropertyInstance getPropertyByName(String name) {
        if (!properties.containsKey(name)) {
            throw new IllegalArgumentException("for entity of type " + entityDefinition.getName() + " has no property named " + name);
        }

        return properties.get(name);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        properties.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }

}
