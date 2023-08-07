package world.factors.expression.util.impl;

import world.factors.expression.util.api.AbstractFunction;

import java.util.ArrayList;

public class FunctionDefinitionImpl extends AbstractFunction {
    private String name;
    private ArrayList<Object> expressions;



    public String getName() {
        return name;
    }

    public ArrayList<Object> getExpressions() {
        return expressions;
    }

    public int getNumExpressions() {
        return expressions.size();
    }
}
