package world.factors.environment;

public class EnvProperty {
    public enum Type {INTEGER, REAL_NUMBER, BOOLEAN_VALUE, CHARACTER_STRING};

    private String name;
    private Type type;
    private Range range;


    public String GetName(){
        return this.name;
    }

}
