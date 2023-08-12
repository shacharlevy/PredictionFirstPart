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


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    }



}


