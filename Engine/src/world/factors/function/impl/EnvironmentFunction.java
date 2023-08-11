package world.factors.function.impl;

import context.Context;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.expression.api.Expression;
import world.factors.function.api.AbstractFunction;
import world.factors.function.api.FunctionType;
import world.factors.property.execution.PropertyInstance;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentFunction extends AbstractFunction {
    public EnvironmentFunction(List<Expression> args) {
        super(FunctionType.ENVIRONMENT, args, 1);
    }

    @Override
    public Object execute(Context context) {
        ActiveEnvironment activeEnvironment = context.getEnvironment();
        String name = (String) this.expressions.get(0).evaluate(context);
        PropertyInstance propertyInstance = activeEnvironment.getProperty(name);
        if (propertyInstance == null) {
            throw new IllegalArgumentException("environment variable [" + name + "] is not exist");
        }
        return propertyInstance.getValue();
    }
}
