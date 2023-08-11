package validator;

import resources.schema.generatedWorld.*;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class XMLValidator {
    public static void validateFileExists(Path file) throws FileNotFoundException {
        if(!file.toFile().exists()) {
            throw new FileNotFoundException(file.getFileName().toString());
        }
    }

    public static void validateFileIsXML(Path file) {
        if(!file.getFileName().endsWith(".xml")) {
            throw new IllegalArgumentException("File is not XML");
        }
    }

    public static void validateXMLContent(PRDWorld world) {
        validateEnvironmentPropertyUniqueness(world);
        validateAllEntitiesPropertyUniqueness(world);
        validateAllActionsReferToExistingEntities(world);
        validateAllActionsReferToExistingEntityProperties(world);
    }

    //2
    public static void validateEnvironmentPropertyUniqueness(PRDWorld world) {
        PRDEvironment environment = world.getPRDEvironment();
        for(int i=0; i<environment.getPRDEnvProperty().size(); i++) {
            String currName = environment.getPRDEnvProperty().get(i).getPRDName();
            for(int j=i+1; j<environment.getPRDEnvProperty().size(); j++) {
                String otherName = environment.getPRDEnvProperty().get(j).getPRDName();
                if(currName.equals(otherName)) {
                    throw new IllegalArgumentException("Environment name is not unique");
                }
            }
        }
    }

    public static void validateEntityNameUniqueness(PRDWorld world) {
        PRDEntities entities = world.getPRDEntities();
        for(int i=0; i<entities.getPRDEntity().size(); i++) {
            String currName = entities.getPRDEntity().get(i).getName();
            for(int j=i+1; j<entities.getPRDEntity().size(); j++) {
                String otherName = entities.getPRDEntity().get(j).getName();
                if(currName.equals(otherName)) {
                    throw new IllegalArgumentException("Entity name is not unique");
                }
            }
        }
    }

    //3

    public static void validateAllEntitiesPropertyUniqueness(PRDWorld world) {
        PRDEntities entities = world.getPRDEntities();
        for(int i=0; i<entities.getPRDEntity().size(); i++) {
            validateEntityPropertyUniqueness(entities.getPRDEntity().get(i));
        }
    }

    private static void validateEntityPropertyUniqueness(PRDEntity entity) {
        PRDProperties properties = entity.getPRDProperties();
        for(int i=0; i<properties.getPRDProperty().size(); i++) {
            String currName = properties.getPRDProperty().get(i).getPRDName();
            for(int j=i+1; j<properties.getPRDProperty().size(); j++) {
                String otherName = properties.getPRDProperty().get(j).getPRDName();
                if(currName.equals(otherName)) {
                    throw new IllegalArgumentException("Entity property name is not unique");
                }
            }
        }
    }
    //4
    public static void validateAllActionsReferToExistingEntities(PRDWorld world) {
        for(PRDRule rule: world.getPRDRules().getPRDRule()) {
            for(PRDAction action: rule.getPRDActions().getPRDAction()) {
                if(!isEntityExist(world, action.getEntity())) {
                    throw new IllegalArgumentException("Action refers to non-existing entity");
                }
            }
        }
    }

    private static boolean isEntityExist(PRDWorld world, String entityName) {
        PRDEntities entities = world.getPRDEntities();
        for(PRDEntity entity: entities.getPRDEntity()) {
            if(entity.getName().equals(entityName)) {
                return true;
            }
        }
        return false;
    }

    //5
    public static void validateAllActionsReferToExistingEntityProperties(PRDWorld world) {
        for(PRDRule rule: world.getPRDRules().getPRDRule()) {
            for(PRDAction action: rule.getPRDActions().getPRDAction()) {
                if(!isEntityPropertyExist(world, action.getEntity(), action.getProperty())) {
                    throw new IllegalArgumentException("Action refers to non-existing entity");
                }
            }
        }
    }

    private static boolean isEntityPropertyExist(PRDWorld world, String entityName, String propertyName) {
        PRDEntities entities = world.getPRDEntities();
        for(PRDEntity entity: entities.getPRDEntity()) {
            if(entity.getName().equals(entityName)) {
                for(PRDProperty property: entity.getPRDProperties().getPRDProperty()) {
                    if(property.getPRDName().equals(propertyName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }




}
