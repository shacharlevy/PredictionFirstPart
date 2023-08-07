package world.factors.expression.util.api;

import java.util.ArrayList;

public interface Function {
    FunctionType getFunctionType();
    Object execute(ArrayList<Object> args);
    int getNumArguments();
}
