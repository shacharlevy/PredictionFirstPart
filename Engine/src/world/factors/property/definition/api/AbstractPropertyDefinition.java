package world.factors.property.definition.api;

import value.generator.api.ValueGenerator;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition{
    protected final String name;
    protected final PropertyType propertyType;
    protected ValueGenerator valueGenerator;

    public AbstractPropertyDefinition(String name, PropertyType propertyType, ValueGenerator valueGenerator) {
        this.name = name;
        this.propertyType = propertyType;
        this.valueGenerator = valueGenerator;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyType getType() {
        return propertyType;
    }

    @Override
    public Object generateValue() {
        return valueGenerator.generateValue();
    }
}
