package world.factors.environment;

import java.util.List;

public class Environment {
    private List<EnvProperty> properties;

    public boolean IsNameAlreadyExist(String name) {

        for(EnvProperty p: this.properties) {

            if (p.GetName() == name){
                return true;
            }
        }

        return false;
    }
}
