package world.factors.property.definition.api;

public abstract class AbstractPropertyDefinition implements PropertyDefinition{
    private final String name;
    private final PropertyType propertyType;

    public AbstractPropertyDefinition(String name, PropertyType propertyType) {
        this.name = name;
        this.propertyType = propertyType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyType getType() {
        return propertyType;
    }
}
