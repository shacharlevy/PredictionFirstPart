package world.factors.function.impl;

import context.Context;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.function.api.AbstractFunction;
import world.factors.function.api.FunctionType;
import world.factors.property.execution.PropertyInstance;

import java.util.ArrayList;

public class EnvironmentFunction extends AbstractFunction {
    public EnvironmentFunction(FunctionType functionType, ArrayList<String> argumentTypes) {
        super(FunctionType.ENVIRONMENT, argumentTypes, 1);
    }

    @Override
    public Object execute(ArrayList<Object> args, Context context) {
        ActiveEnvironment activeEnvironment = context.getEnvironment();
        String name = (String) args.get(0);
        PropertyInstance propertyInstance = activeEnvironment.getProperty(name);
        if (propertyInstance == null) {
            throw new IllegalArgumentException("environment variable [" + name + "] is not exist");
        }
        return propertyInstance;
    }
}
