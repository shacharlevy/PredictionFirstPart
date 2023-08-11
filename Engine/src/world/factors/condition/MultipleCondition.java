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
        switch (this.logical){
            case AND:
                for (SingleCondition singleCondition : singleConditions) {
                    if (!singleCondition.assertCondition(context))
                        return false;
                }
                return true;
            case OR:
                for (SingleCondition singleCondition : singleConditions) {
                    if (singleCondition.assertCondition(context))
                        return true;
                }
                return false;
        }
        return false;
    }

}
