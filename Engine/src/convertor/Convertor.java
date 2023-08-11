package convertor;

import resources.schema.generatedWorld.*;
import value.generator.api.ValueGenerator;
import value.generator.api.ValueGeneratorFactory;
import value.generator.fixed.FixedValueGenerator;
import world.World;
import world.factors.action.api.Action;
import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.definition.EntityDefinitionImpl;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.property.definition.api.EntityPropertyDefinition;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.definition.api.Range;
import world.factors.property.definition.impl.entity.BooleanEntityPropertyDefinition;
import world.factors.property.definition.impl.entity.FloatEntityPropertyDefinition;
import world.factors.property.definition.impl.entity.IntegerEntityPropertyDefinition;
import world.factors.property.definition.impl.entity.StringEntityPropertyDefinition;
import world.factors.property.definition.impl.env.BooleanEnvPropertyDefinition;
import world.factors.property.definition.impl.env.FloatEnvPropertyDefinition;
import world.factors.property.definition.impl.env.IntegerEnvPropertyDefinition;
import world.factors.property.definition.impl.env.StringEnvPropertyDefinition;
import world.factors.rule.Activation;
import world.factors.rule.Rule;
import world.factors.rule.RuleImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
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

    private EnvVariablesManager getEnvironment() {
        EnvVariablesManager environment = new EnvVariableManagerImpl();
        for (PRDEnvProperty envProp : generatedWorld.getPRDEvironment().getPRDEnvProperty()) {
            if (envProp.getType().equals("decimal")) {
                environment.addEnvironmentVariable(new IntegerEnvPropertyDefinition(envProp.getPRDName(), new Range(envProp.getPRDRange().getTo(), envProp.getPRDRange().getFrom())));
            } else if (envProp.getType().equals("float")) {
                environment.addEnvironmentVariable(new FloatEnvPropertyDefinition(envProp.getPRDName(), new Range(envProp.getPRDRange().getTo(), envProp.getPRDRange().getFrom())));
            } else if (envProp.getType().equals("boolean")) {
                environment.addEnvironmentVariable(new BooleanEnvPropertyDefinition(envProp.getPRDName()));
            } else if (envProp.getType().equals("string")) {
                environment.addEnvironmentVariable(new StringEnvPropertyDefinition(envProp.getPRDName()));
            } else {
                throw new RuntimeException("Unknown type: " + envProp.getType());
            }
        }
        return environment;
    }

    private List<EntityDefinition> getEntities() {
        List<EntityDefinition> entities = new ArrayList<>();
        for (PRDEntity entity: generatedWorld.getPRDEntities().getPRDEntity()) {
            entities.add(getEntity(entity));
        }
        return entities;
    }

    private EntityDefinition getEntity(PRDEntity entity) {
        EntityDefinition entityDefinition = new EntityDefinitionImpl(entity.getName(), entity.getPRDPopulation());
        for (PRDProperty property: entity.getPRDProperties().getPRDProperty()) {
            if(property.getPRDValue().isRandomInitialize()){
                entityDefinition.addProperty(getRandomProperty(property));
            }
            else{
                entityDefinition.addProperty(getFixedProperty(property));
            }
        }
        return entityDefinition;
    }

    private EntityPropertyDefinition getRandomProperty(PRDProperty property){
        if(property.getType().equals("decimal")){
            return new IntegerEntityPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createRandomInteger((int)property.getPRDRange().getFrom(), (int)property.getPRDRange().getTo()), new Range<Integer>((int)property.getPRDRange().getTo(), (int)property.getPRDRange().getFrom()));
        }
        else if(property.getType().equals("float")){
            return new FloatEntityPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createRandomFloat((float)property.getPRDRange().getFrom(), (float)property.getPRDRange().getTo()), new Range<Float>((float)property.getPRDRange().getTo(), (float)property.getPRDRange().getFrom()));
        }
        else if(property.getType().equals("boolean")){
            return new BooleanEntityPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createRandomBoolean());
        }
        else if(property.getType().equals("string")){
            return new StringEntityPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createRandomString());
        }
        else{
            throw new RuntimeException("Unknown type: " + property.getType());
        }
    }

    private EntityPropertyDefinition getFixedProperty(PRDProperty property) {
        if(property.getType().equals("decimal")){
            return new IntegerEntityPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createFixed(Integer.valueOf(property.getPRDValue().getInit())), new Range<Integer>((int)property.getPRDRange().getTo(), (int)property.getPRDRange().getFrom()));
        }
        else if(property.getType().equals("float")){
            return new FloatEntityPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createFixed(Float.valueOf(property.getPRDValue().getInit())), new Range<Float>((float)property.getPRDRange().getTo(), (float)property.getPRDRange().getFrom()));
        }
        else if(property.getType().equals("boolean")){
            return new BooleanEntityPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createFixed(Boolean.valueOf(property.getPRDValue().getInit())));
        }
        else if(property.getType().equals("string")){
            return new StringEntityPropertyDefinition(property.getPRDName(), ValueGeneratorFactory.createFixed(property.getPRDValue().getInit()));
        }
        else{
            throw new RuntimeException("Unknown type: " + property.getType());
        }
    }

    private List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();
        for (PRDRule rule: generatedWorld.getPRDRules().getPRDRule()) {
            rules.add(getRule(rule));
        }
        return rules;
    }

    private Rule getRule(PRDRule rule) {
        String name = rule.getName();
        Activation activation = new Activation(rule.getPRDActivation().getTicks(), rule.getPRDActivation().getProbability());
        Rule ruleImpl = new RuleImpl(name, activation);
        for (PRDAction action: rule.getPRDActions().getPRDAction()) {
            ruleImpl.addAction(getAction(action));
        }
        return ruleImpl;
    }

    private Action getAction(PRDAction action) {

    }

    public void setGeneratedWorld(PRDWorld generatedWorld) {
        this.generatedWorld = generatedWorld;
    }

    private World convertPRDWorldToWorld() {
        EnvVariablesManager environment = getEnvironment();
        List<EntityDefinition> entities = getEntities();
        List<Rule> rules = getRules();

    }
}
