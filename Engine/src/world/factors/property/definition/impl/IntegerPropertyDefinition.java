package world.factors.property.definition.impl;

import world.factors.property.definition.api.AbstractPropertyDefinition;
import world.factors.property.definition.api.PropertyType;
import value.generator.api.ValueGenerator;

public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {

    public IntegerPropertyDefinition(String name, ValueGenerator<Integer> valueGenerator) {
        super(name, PropertyType.DECIMAL, valueGenerator);
    }

}
