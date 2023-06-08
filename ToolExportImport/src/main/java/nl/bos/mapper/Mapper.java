package nl.bos.mapper;

import nl.bos.message.Log;
import nl.bos.validation.IValidator;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Mapper implements IValidator {
    private static Mapper instance;
    MapperModel mapperModel;

    private Mapper() {
        //Hide for Singleton
    }

    public static Mapper getInstance() {
        if (instance == null)
            instance = new Mapper();
        return instance;
    }

    @Override
    public boolean isReady() throws JAXBException, IOException, SAXException {
        if (instance == null) {
            return false;
        }

        loadMappingFile();
        return true;
    }

    private void loadMappingFile() throws IOException, JAXBException, SAXException {
        InputStream xmlStream = Mapper.class.getClassLoader().getResourceAsStream("mapping.xml");
        InputStream xsdStream = Mapper.class.getClassLoader().getResourceAsStream("mapping.xsd");

        if (xmlStream == null) {
            throw new FileNotFoundException(String.format("Resource %s not found", "mapping.xml"));
        }
        if (xsdStream == null) {
            throw new FileNotFoundException(String.format("Resource %s not found", "mapping.xsd"));
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(MapperModel.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema mappingSchema = sf.newSchema(new StreamSource(xsdStream));
        jaxbUnmarshaller.setSchema(mappingSchema);

        mapperModel = (MapperModel) jaxbUnmarshaller.unmarshal(xmlStream);
        Log.getInstance().send(String.valueOf(mapperModel));

        xmlStream.close();
        xsdStream.close();
    }

    public MapperModel getMapperModel() {
        return mapperModel;
    }
}
