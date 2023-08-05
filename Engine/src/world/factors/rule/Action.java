package world.factors.rule;

import world.factors.entity.EntityDefinition;
import world.factors.entity.Property;

public class Action {
    public enum Type {INCREASE, DECREASE, CALCULATION, CONDITION, SET, KILL, REPLACE, PROXIMITY};

    private EntityDefinition entity;
    private Property property;
    private Type type;
    private String by;
    private float probability;

}
