package world.factors.property.definition.impl;

import value.generator.api.ValueGenerator;
import world.factors.property.definition.api.AbstractPropertyDefinition;
import world.factors.property.definition.api.PropertyType;

public class FloatPropertyDefinition extends AbstractPropertyDefinition<Float> {
    public FloatPropertyDefinition(String name, ValueGenerator<Float> valueGenerator) {
        super(name, PropertyType.FLOAT, valueGenerator);
    }
}
