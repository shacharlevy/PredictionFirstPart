package convertor;

import context.Context;
import context.ContextImpl;
import resources.schema.generatedWorld.*;
import value.generator.api.ValueGenerator;
import value.generator.api.ValueGeneratorFactory;
import value.generator.fixed.FixedValueGenerator;
import world.World;
import world.factors.action.api.AbstractAction;
import world.factors.action.api.Action;
import world.factors.action.api.ActionType;
import world.factors.action.impl.*;
import world.factors.condition.*;
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
import world.factors.termination.Termination;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Convertor {
    private PRDWorld generatedWorld;

    public World convertPRDWorldToWorld() {
        EnvVariablesManager environment = getEnvironment();
        List<EntityDefinition> entities = getEntities();
        List<Rule> rules = getRules(entities);
        Termination termination = getTermination();
        return new World(environment, entities, rules, termination);
    }

    private EnvVariablesManager getEnvironment() {
        EnvVariablesManager environment = new EnvVariableManagerImpl();
        for (PRDEnvProperty envProp : generatedWorld.getPRDEvironment().getPRDEnvProperty()) {
            if (envProp.getType().equals("decimal")) {
                if (!isConvertableToInteger(envProp.getPRDRange().getFrom()) || !isConvertableToInteger(envProp.getPRDRange().getTo())) {
                    throw new RuntimeException("Range of decimal environment property must be integer");
                }
                environment.addEnvironmentVariable(new IntegerEnvPropertyDefinition(envProp.getPRDName(), new Range((int)envProp.getPRDRange().getTo(), (int)envProp.getPRDRange().getFrom())));
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

    private boolean isConvertableToInteger(Double value) {
        return value == Math.floor(value);
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

    private List<Rule> getRules(List<EntityDefinition> entities) {
        List<Rule> rules = new ArrayList<>();
        for (PRDRule rule: generatedWorld.getPRDRules().getPRDRule()) {
            rules.add(getRule(rule, entities));
        }
        return rules;
    }

    private Rule getRule(PRDRule rule, List<EntityDefinition> entities) {
        String name = rule.getName();
        Activation activation = getActivation(rule);
        Rule ruleImpl = new RuleImpl(name, activation);
        for (PRDAction action: rule.getPRDActions().getPRDAction()) {
            ruleImpl.addAction(getAction(action, entities));
        }
        return ruleImpl;
    }

    private Activation getActivation(PRDRule rule) {
        //if both, one null, or both not null
        if (rule.getPRDActivation() == null) {
            return new Activation();
        } else if (rule.getPRDActivation().getTicks() != null && rule.getPRDActivation().getProbability() != null) {
            return new Activation(rule.getPRDActivation().getTicks(), rule.getPRDActivation().getProbability());
        } else if (rule.getPRDActivation().getTicks() != null) {
            return new Activation(rule.getPRDActivation().getTicks());
        } else if (rule.getPRDActivation().getProbability() != null) {
            return new Activation(rule.getPRDActivation().getProbability());
        } else {
            return new Activation();
        }
    }

    private Action getAction(PRDAction action, List<EntityDefinition> entities) {
        ActionType actionType = ActionType.getActionType(action.getType());
        EntityDefinition entityDefinition = getEntityDefinition(action.getEntity(), entities);
        switch (actionType) {
            case INCREASE:
                return getIncreaseAction(action, entityDefinition);
            case DECREASE:
                return getDecreaseAction(action, entityDefinition);
            case CALCULATION:
                return getCalculationAction(action, entityDefinition);
            case CONDITION:
                return getConditionAction(action, entityDefinition, entities);
            case SET:
                return getSetAction(action, entityDefinition);
            case KILL:
                return getKillAction(action, entityDefinition);
            default:
                throw new RuntimeException("Unknown action type: " + actionType);
        }
    }

    private Action getKillAction(PRDAction action, EntityDefinition entityDefinition) {
        return new KillAction(entityDefinition);
    }


    private Action getSetAction(PRDAction action, EntityDefinition entityDefinition) {
        String property = action.getProperty();
        String value = action.getValue();
        return new SetAction(entityDefinition, property, value);
    }

    private Action getConditionAction(PRDAction action, EntityDefinition entityDefinition, List<EntityDefinition> entities) {
        Condition condition = getCondition(action.getPRDCondition(), entities);
        List<AbstractAction> thenActions = getActions(action.getPRDThen().getPRDAction(), entities);
        List<AbstractAction> elseActions = null;
        if (action.getPRDElse() != null) {
            elseActions = getActions(action.getPRDElse().getPRDAction(), entities);
        }
        return new ConditionAction(entityDefinition, condition, thenActions, elseActions);
    }

    private List<AbstractAction> getActions(List<PRDAction> prdAction, List<EntityDefinition> entities) {
        List<AbstractAction> actions = new ArrayList<>();
        for (PRDAction action: prdAction) {
            actions.add((AbstractAction) getAction(action, entities));
        }
        return actions;
    }

    private Condition getCondition(PRDCondition prdCondition, List<EntityDefinition> entities) {
        String singularity = prdCondition.getSingularity();
        if (singularity.equals("single")) {
            EntityDefinition entityDefinition = getEntityDefinition(prdCondition.getEntity(), entities);
            EntityPropertyDefinition property = getEntityPropertyDefinition(prdCondition.getProperty(), entityDefinition.getProps());
            OperatorType operatorType = OperatorType.getOperatorType(prdCondition.getOperator());
            String value = prdCondition.getValue();
            return new SingleCondition(entityDefinition, property, operatorType, value);
        } else if (singularity.equals("multiple")) {
            LogicalType logicalType = LogicalType.getLogicalType(prdCondition.getLogical());
            List<Condition> conditions = new ArrayList<>();
            for (PRDCondition condition: prdCondition.getPRDCondition()) {
                conditions.add(getCondition(condition, entities));
            }
            return new MultipleCondition(logicalType, conditions);
        } else {
            throw new RuntimeException("Unknown singularity: " + singularity);
        }
    }

    private EntityPropertyDefinition getEntityPropertyDefinition(String property, List<EntityPropertyDefinition> props) {
        List<EntityPropertyDefinition> filteredProps = props
                .stream()
                .filter(entityPropertyDefinition -> entityPropertyDefinition.getName().equals(property))
                .collect(Collectors.toList());
        if (filteredProps.size() != 1) {
            throw new RuntimeException("There is no property named " + property + " or there are more than one");
        }
        return filteredProps.get(0);
    }

    private Action getCalculationAction(PRDAction action, EntityDefinition entityDefinition) {
        String resultProperty = action.getResultProp();
        if (action.getPRDMultiply() != null) {
            String argument1 = action.getPRDMultiply().getArg1();
            String argument2 = action.getPRDMultiply().getArg2();
            return new CalculationAction(entityDefinition, resultProperty, argument1, argument2, CalculationAction.CalculationOperator.MULTIPLY);
        } else /*if (action.getPRDDivide() != null) */{
            String argument1 = action.getPRDDivide().getArg1();
            String argument2 = action.getPRDDivide().getArg2();
            return new CalculationAction(entityDefinition, resultProperty, argument1, argument2, CalculationAction.CalculationOperator.DIVIDE);
        }
    }

    private Action getDecreaseAction(PRDAction action, EntityDefinition entityDefinition) {
        String property = action.getProperty();
        String byExpression = action.getBy();
        return new DecreaseAction(entityDefinition, property, byExpression);
    }

    private EntityDefinition getEntityDefinition(String entity, List<EntityDefinition> entities) {
        List<EntityDefinition> filteredEntities = entities
                .stream()
                .filter(entityDefinition -> entityDefinition.getName().equals(entity))
                .collect(Collectors.toList());
        if (filteredEntities.size() != 1) {
            throw new RuntimeException("There is no entity named " + entity + " or there are more than one");
        }
        return filteredEntities.get(0);
    }

    private Action getIncreaseAction(PRDAction action, EntityDefinition entityDefinition) {
        String property = action.getProperty();
        String byExpression = action.getBy();
        return new IncreaseAction(entityDefinition, property, byExpression);
    }

    private Termination getTermination() {
        Termination termination = new Termination();
        List<Object> terminationList = generatedWorld.getPRDTermination().getPRDByTicksOrPRDBySecond();
        if (terminationList.size() > 2 || terminationList.size() == 0) {
            throw new RuntimeException("Termination must have exactly 1 or 2 termination types");
        }
        for (Object terminationObject: terminationList) {
            if (terminationObject instanceof PRDByTicks) {
                termination.setTicksCount(((PRDByTicks) terminationObject).getCount());
            } else if (terminationObject instanceof PRDBySecond) {
                termination.setSecondsCount(((PRDBySecond) terminationObject).getCount());
            } else {
                throw new RuntimeException("Unknown termination type: " + terminationObject.getClass());
            }
        }
        return termination;
    }

    public void setGeneratedWorld(PRDWorld generatedWorld) {
        this.generatedWorld = generatedWorld;
    }

}
