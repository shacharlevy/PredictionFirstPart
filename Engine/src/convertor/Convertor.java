package convertor;

import resources.schema.generatedWorld.PRDEnvProperty;
import resources.schema.generatedWorld.PRDWorld;
import value.generator.fixed.FixedValueGenerator;
import world.World;
import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.definition.impl.IntegerPropertyDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Convertor {
    private PRDWorld generatedWorld;

    public Convertor(PRDWorld generatedWorld) {
        this.generatedWorld = generatedWorld;
    }

    public World convert() {
        EnvVariablesManager environment = new EnvVariableManagerImpl();
        List<EntityDefinition> entities = new ArrayList<>(generatedWorld.getPRDEntities());
        return new World(environment, entities);
    }

    private EnvVariableManagerImpl getEnvironment() {
        Map<String, PropertyDefinition> propNameToPropDefinition = new HashMap<>();
        for (PRDEnvProperty envProp : generatedWorld.getPRDEvironment().getPRDEnvProperty()) {
            if(envProp.getType().equals("decimal")) {
                propNameToPropDefinition.put(envProp.getPRDName(), new IntegerPropertyDefinition(envProp.getPRDName(), new FixedValueGenerator<>())));
            } else if(envProp.getType().equals("float")) {
                propNameToPropDefinition.put(envProp.getName(), new PropertyDefinition(envProp.getName(), Double.parseDouble(envProp.getValue())));
            } else if(envProp.getType().equals("string")) {
                propNameToPropDefinition.put(envProp.getName(), new PropertyDefinition(envProp.getName(), envProp.getValue()));
            } else if(envProp.getType().equals("boolean")) {
                propNameToPropDefinition.put(envProp.getName(), new PropertyDefinition(envProp.getName(), Boolean.parseBoolean(envProp.getValue())));
            }

        return new EnvVariableManagerImpl(generatedWorld
                .getPRDEnvironment()
                .getPRDEnvProperty()
                .stream()
                .map(envProp -> )
                .collect(Collectors.toList()));
    }

    private void convertPRDWorldToWorld(PRDWorld generatedWorld) {
        EnvVariablesManager environment = new EnvVariableManagerImpl(generatedWorld
                .getPRDEvironment().getPRDEnvProperty()
                .stream()
                .map(envProp -> )
                .collect(Collectors.toList()));
        List<EntityDefinition> entities = new ArrayList<>(generatedWorld.getPRDEntities());

    }
}
