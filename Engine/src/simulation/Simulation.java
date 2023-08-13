package simulation;

import world.World;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.execution.EntityInstance;
import world.factors.entity.execution.EntityInstanceImpl;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.entity.execution.manager.EntityInstanceManagerImpl;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.property.definition.api.EntityPropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final ActiveEnvironment activeEnvironment;
    private final World world;
    private EntityInstanceManager entityInstanceManager;

    public Simulation(ActiveEnvironment activeEnvironment, World world) {
        this.activeEnvironment = activeEnvironment;
        this.world = world;
        this.entityInstanceManager = new EntityInstanceManagerImpl();
    }
    private void initEntityInstancesArray() {
        EntityDefinition entityDefinition = world.getEntities().get(0);
        for (int i = 0; i < entityDefinition.getPopulation(); i++) {
            entityInstanceManager.create(entityDefinition);
        }
    }

    public String run() {
        initEntityInstancesArray();
        boolean isRunning = true;
        int currentTick = 0;
        while (isRunning) {
            for (EntityInstance entityInstance : entityInstanceManager.getInstances()) {
                if (entityInstance.isAlive()) {
                    entityInstance.execute(world, activeEnvironment);
                }
            }
            isRunning = false;
            for (EntityInstance entityInstance : entityInstanceManager.getInstances()) {
                if (entityInstance.isAlive()) {
                    isRunning = true;
                    break;
                }
            }
        }
    }

}
