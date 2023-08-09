package context;

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
    private Expression getExpressionByString(String expression) {
        if (isFunctionExpression(expression)) {
            Function function = getFunctionByExpression(expression);
            return new UtilFunctionExpression(expression, function, this);
        }
        //TODO: check the structure od property expression
        else if (primaryEntityInstance.getPropertyByName(expression) == null) {
            return new PropertyNameExpression();
        }
        else {
            return new FreeValueExpression();
        }
    }
    @Override
    public Function getFunctionByExpression(String functionExpression) {
        // this function receives only function expression
        // the function expression structure is: functionName(arg1,arg2,arg3,...)
        // so we need to extract the function name and the arguments
        List<String> elements = splitExpressionString(functionExpression);
        List<Expression> args = new ArrayList<>();
        for (String element : elements) {
            args.add(getExpression(element)));
        }
        switch(FunctionType.getFunctionType(elements.get(0))) {
            case ENVIRONMENT:
                if (elements.size() != 2) {
                throw new IllegalArgumentException("environment function must have only one argument");
            }
            if (activeEnvironment.getProperty(elements.get(1)) == null) {
                throw new IllegalArgumentException("environment function argument must be a valid environment property");
            }
            args.add(
                return new world.factors.function.impl.EnvironmentFunction((ArrayList<String>) elements);
            case RANDOM:
                return new world.factors.function.impl.RandomFunction((ArrayList<String>) elements);
        }

    }
    public static List<String> splitExpressionString(String expression) {
        List<String> elements = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\w+\\([^()]*\\)");
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()) {
            elements.add(matcher.group());
        }

        return elements;
    }
}