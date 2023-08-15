package simulation;

import context.Context;
import context.ContextImpl;
import world.World;
import world.factors.action.api.Action;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.execution.EntityInstance;
import world.factors.entity.execution.EntityInstanceImpl;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.entity.execution.manager.EntityInstanceManagerImpl;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.property.definition.api.EntityPropertyDefinition;
import world.factors.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final ActiveEnvironment activeEnvironment;
    private final World world;
    private final EntityInstanceManager entityInstanceManager;


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
        while (this.world.getTermination().isRunning(currentTick)) {
            currentTick++;
            for (Rule rule: this.world.getRules()){
                if (rule.isRuleActive(currentTick)){
                    for (EntityInstance entityInstance : this.entityInstanceManager.getInstances()) {
                        for (Action action: rule.getActionsToPerform()){
                            action.invoke(new ContextImpl(entityInstance, entityInstanceManager, this.activeEnvironment));
                        }
                    }
                }
            }
        }
       return
    }

}
