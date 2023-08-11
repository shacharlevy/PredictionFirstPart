package world.factors.entity.execution.manager;


import world.factors.entity.definition.EntityDefinition;
import world.factors.property.definition.api.EntityPropertyDefinition;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.entity.execution.EntityInstance;
import world.factors.entity.execution.EntityInstanceImpl;
import world.factors.property.execution.PropertyInstance;
import world.factors.property.execution.PropertyInstanceImpl;

import java.util.ArrayList;
import java.util.List;

public class EntityInstanceManagerImpl implements EntityInstanceManager {

    private int count;
    private List<EntityInstance> instances;

    public EntityInstanceManagerImpl() {
        count = 0;
        instances = new ArrayList<>();
    }

    @Override
    public EntityInstance create(EntityDefinition entityDefinition) {

        count++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, count);
        instances.add(newEntityInstance);

        for (EntityPropertyDefinition entityPropertyDefinition : entityDefinition.getProps()) {
            Object value = entityPropertyDefinition.generateValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(entityPropertyDefinition, value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }

        return newEntityInstance;
    }


    @Override
    public List<EntityInstance> getInstances() {
        return instances;
    }

    @Override
    public EntityInstance getEntityInstanceByName(String entityName) {
        for (EntityInstance entityInstance : instances) {
            if (entityInstance.getEntityDefinition().getName().equals(entityName)) {
                return entityInstance;
            }
        }
        return null;
    }

    @Override
    public void killEntity(int id) {
        // some implementation...
    }
}
