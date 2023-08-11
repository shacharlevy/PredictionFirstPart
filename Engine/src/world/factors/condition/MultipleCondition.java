package world.factors.condition;

import context.Context;

import java.util.List;

public class MultipleCondition implements Condition{
    private final LogicalType logical;
    private final List<SingleCondition> singleConditions;

    public MultipleCondition(LogicalType logical, List<SingleCondition> singleConditions) {
        this.logical = logical;
        this.singleConditions = singleConditions;
    }

    @Override
    public boolean assertCondition(Context context) {
        return false;
    }
}
