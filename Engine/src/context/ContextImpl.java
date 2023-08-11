package context;

import world.factors.entity.definition.EntityDefinition;
import world.factors.entity.execution.EntityInstance;
import world.factors.entity.execution.manager.EntityInstanceManager;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.expression.api.Expression;
import world.factors.expression.api.ExpressionType;
import world.factors.expression.impl.FreeValueExpression;
import world.factors.expression.impl.PropertyNameExpression;
import world.factors.expression.impl.UtilFunctionExpression;
import world.factors.function.api.Function;
import world.factors.function.api.FunctionType;
import world.factors.function.impl.EnvironmentFunction;
import world.factors.function.impl.RandomFunction;
import world.factors.property.execution.PropertyInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContextImpl implements Context {

    private EntityInstance primaryEntityInstance;
    private EntityInstanceManager entityInstanceManager;
    private ActiveEnvironment activeEnvironment;

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }

    @Override
    public ActiveEnvironment getEnvironment() {
        return activeEnvironment;
    }

    @Override
    public ExpressionType getExpressionType(String expression) {
        if (isFunctionExpression(expression)) {
            String functionName = expression.substring(0, expression.indexOf("("));
            if (FunctionType.isFunctionType(functionName)) {
                return ExpressionType.UTIL_FUNCTION;
            }
        }
        else if (isPropertyName(expression)) {
            return ExpressionType.PROPERTY_NAME;
        }
        return ExpressionType.FREE_VALUE;
    }
    //TODO: move to validator later
    private boolean isFunctionExpression(String expression) {
        if (expression.charAt(expression.length() - 1) != ')') {
            return false;
        }
        int firstOpenParenthesis = expression.indexOf('(');
        if (firstOpenParenthesis == -1) {
            return false;
        }
        return true;
    }

    private boolean isPropertyName(String expression) {
        try {
            primaryEntityInstance.getPropertyByName(expression);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public FunctionType getFunctionType(String expression) {
        String functionName = expression.substring(0, expression.indexOf("("));
        return FunctionType.getFunctionType(functionName);
    }
    @Override
    public Expression getExpressionByString(String expression) {
        if (isFunctionExpression(expression)) {
            return new UtilFunctionExpression(expression, this);
        }
        else if (primaryEntityInstance.getPropertyByName(expression) == null) {
            return new PropertyNameExpression(expression, this);
        }
        else {
            return new FreeValueExpression(expression, this);
        }
    }
    @Override
    public Function getFunctionByExpression(String functionExpression) {
        // this function receives only function expression
        // the function expression structure is: functionName(arg1,arg2,arg3,...)
        // so we need to extract the function name and the arguments
        List<String> elements = splitExpressionString(functionExpression);
        List<Expression> args = new ArrayList<>();
        for (int i = 1; i < elements.size(); i++) {
            args.add(getExpressionByString(elements.get(i)));
        }
        switch(FunctionType.getFunctionType(elements.get(0))) {
            case ENVIRONMENT:
                if (elements.size() != 2) {
                throw new IllegalArgumentException("environment function must have only one argument");
                }
                if (activeEnvironment.getProperty(elements.get(1)) == null) {
                   throw new IllegalArgumentException("environment function argument must be a valid environment property");
                }
                return new EnvironmentFunction(args);
            case RANDOM:
                if (elements.size() != 2) {
                    throw new IllegalArgumentException("random function must have only one argument");
                }
                return new RandomFunction(args);
            default:
                throw new IllegalArgumentException("function [" + elements.get(0) + "] is not exist");
        }
    }
    public static List<String> splitExpressionString(String expression) {
        List<String> elements = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\w+\\([^()]*\\)");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            elements.add(matcher.group());
        }

        return elements;
    }

    @Override
    public PropertyInstance getPropertyByName(String expression) {
        return primaryEntityInstance.getPropertyByName(expression);
    }

    @Override
    public Object getValueByExpression(Expression expression) {
        switch (expression.getExpressionType()) {
            case UTIL_FUNCTION:
                return ((Function)expression.evaluate(this)).execute(this);
            case PROPERTY_NAME:
                return ((PropertyInstance)expression.evaluate(this)).getValue();
            case FREE_VALUE:
                return expression.evaluate(this);
            default:
                throw new IllegalArgumentException("expression type [" + expression.getExpressionType() + "] is not exist");
        }
    }
}