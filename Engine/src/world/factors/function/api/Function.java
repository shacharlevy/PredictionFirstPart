package world.factors.function.api;

import context.Context;

import java.util.ArrayList;

public interface Function {
    FunctionType getFunctionType();
    Object execute(Context context);
    int getNumArguments();
}
