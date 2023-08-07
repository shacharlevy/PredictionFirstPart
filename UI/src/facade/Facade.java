package facade;

import resources.schema.generatedWorld.PRDWorld;
import static validator.XMLValidator.*;
import world.World;
import world.factors.environment.Environment;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Facade {
    World world = new World();
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

    public static void loadXML(String path) throws FileNotFoundException {
        Path xmlPath = Paths.get(path);
        validateFileExists(xmlPath);
        validateFileIsXML(xmlPath);
        PRDWorld generatedWorld = fromXmlFileToObject(xmlPath);
        validateXMLContent(generatedWorld);

    }

    private World convertPRDWorldToWorld(PRDWorld generatedWorld) {
        Environment environment = new Environment(generatedWorld.getEnvironment());

        return new World();
    }

}


