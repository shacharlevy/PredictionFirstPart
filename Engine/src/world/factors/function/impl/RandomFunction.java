package world.factors.function.impl;

import context.Context;
import world.factors.expression.api.Expression;
import world.factors.function.api.AbstractFunction;
import world.factors.function.api.FunctionType;

import java.util.ArrayList;

public class RandomFunction extends AbstractFunction {
    public RandomFunction(ArrayList<Expression> args) {
        super(FunctionType.RANDOM, args, 1);
    }

    @Override
    public Object execute(ArrayList<Object> args, Context context) {
        //validate that the argument is an Integer
        //the function will return a random number between 0 and the argument (inclusive)
        if (!(args.get(0) instanceof Integer)) {
            throw new IllegalArgumentException("argument must be a Integer");
        }
        return (int) (Math.random() * ((Integer) args.get(0) + 1));
    }
}
