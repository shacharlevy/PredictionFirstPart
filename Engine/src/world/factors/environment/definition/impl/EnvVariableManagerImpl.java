package world.factors.environment.definition.impl;

import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.environment.execution.impl.ActiveEnvironmentImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnvVariableManagerImpl implements EnvVariablesManager {

    private final Map<String, PropertyDefinition> propNameToPropDefinition;

    public EnvVariableManagerImpl() {
        propNameToPropDefinition = new HashMap<>();
    }

    @Override
    public void addEnvironmentVariable(PropertyDefinition propertyDefinition) {
        propNameToPropDefinition.put(propertyDefinition.getName(), propertyDefinition);
    }

    @Override
    public ActiveEnvironment createActiveEnvironment() {
        return new ActiveEnvironmentImpl();
    }

    @Override
    public Collection<PropertyDefinition> getEnvVariables() {
        return propNameToPropDefinition.values();
    }
}
