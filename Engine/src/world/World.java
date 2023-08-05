package world;

import world.factors.entity.EntityDefinition;
import world.factors.environment.Environment;
import world.factors.rule.Rule;
import world.factors.termination.Termination;

import java.util.List;

public class World {
    private Environment environment;
    private List<EntityDefinition> entities;
    private List<Rule> rules;
    private Termination termination;
}
