package facade;

import context.Context;
import context.ContextImpl;
import convertor.Convertor;
import resources.schema.generatedWorld.PRDWorld;
import static validator.XMLValidator.*;
import world.World;
import world.factors.entity.definition.EntityDefinition;
import world.factors.environment.definition.api.EnvVariablesManager;
import world.factors.environment.definition.impl.EnvVariableManagerImpl;
import world.factors.environment.execution.api.ActiveEnvironment;
import world.factors.property.definition.api.PropertyDefinition;
import world.factors.property.execution.PropertyInstance;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Facade {
    World world;
    Convertor convertor;

    public Facade() {
        this.world = null;
        this.convertor = new Convertor();
    }

    private static PRDWorld fromXmlFileToObject(Path path) {
        try {
            File file = new File(path.toString());
            JAXBContext jaxbContext = JAXBContext.newInstance(PRDWorld.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PRDWorld generatedWorld = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
            return generatedWorld;

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void loadXML(String path) throws FileNotFoundException {
        Path xmlPath = Paths.get(path);
        validateFileExists(xmlPath);
        validateFileIsXML(xmlPath);
        PRDWorld generatedWorld = fromXmlFileToObject(xmlPath);
        validateXMLContent(generatedWorld);
        this.convertor.setGeneratedWorld(generatedWorld);
        this.world = convertor.convertPRDWorldToWorld();
        validateMathActionHasNumericArgs(this.world.getRules(), this.world.getEntities(), (EnvVariableManagerImpl) this.world.getEnvironment());

    }


    public String getSimulationDetails() {
        // present the simulation details
        // all the entities: name, population, properties(name, type, range(if exists), valueGenerated)
        // all rules: name, activation condition, number of actions, list of names of actions
        // ending condition: description
        System.out.println(
                "Simulation Details:\n"
                + "--------------------------------------\n"
                + "Entities:\n"
                + "--------------------------------------\n");
        for (EntityDefinition entity : this.world.getEntities()) {
            System.out.println(entity.toString());
        }
    }

    public void activateSimulation() {
        // present all the environment variables and let the user provide values for each one
        getEnvVariablesFromUser();
    }

    private void getEnvVariablesFromUser() {
        ActiveEnvironment activeEnvironment = this.world.getEnvironment().createActiveEnvironment();
        PropertyInstance propertyInstance;
        Collection<PropertyDefinition> envVariables = this.world.getEnvironment().getEnvVariables();
        for (PropertyDefinition propertyDefinition : envVariables) {
            PropertyDefinitionDTO propertyDefinitionDTO; //display the property definition to the user
            // get the value from the user
            propertyInstance = new PropertyInstance(propertyDefinition, propertyDefinitionDTO.getValue());
            activeEnvironment.addPropertyInstance(propertyInstance);
        }
    }


}


