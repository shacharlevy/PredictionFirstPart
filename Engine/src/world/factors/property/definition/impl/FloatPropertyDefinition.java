package world.factors.property.definition.impl;

import value.generator.api.ValueGenerator;
import world.factors.property.definition.api.AbstractNumericPropertyDefinition;
import world.factors.property.definition.api.Range;
import world.factors.property.definition.api.PropertyType;

public class FloatPropertyDefinition extends AbstractNumericPropertyDefinition<Float> {
    public FloatPropertyDefinition(String name, ValueGenerator<Float> valueGenerator, Range range) {
        super(name, PropertyType.FLOAT, valueGenerator, range);
    }

    @Override
    public boolean isNumeric() {
        return true;
    }
}
