package facade;

import resources.schema.generatedWorld.PRDWorld;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Facade {

    private static void fromXmlFileToObject(String path) {
        System.out.println("\nFrom File to Object");
        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(PRDWorld.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PRDWorld generatedWorld = (PRDWorld) jaxbUnmarshaller.unmarshal(file);
            System.out.println(generatedWorld);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public static void loadXML(String path){
        fromXmlFileToObject(path);
    }

}


