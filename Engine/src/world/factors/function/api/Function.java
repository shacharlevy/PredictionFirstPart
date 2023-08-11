package world.factors.function.api;

import context.Context;

import java.util.ArrayList;

public interface Function {
    FunctionType getFunctionType();
    Object execute(ArrayList<Object> args, Context context);
    int getNumArguments();
}
