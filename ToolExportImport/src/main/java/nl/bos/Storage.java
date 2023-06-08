package nl.bos;

import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import nl.bos.item.IItem;
import nl.bos.validation.IValidator;
import org.apache.commons.configuration.ConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class Storage implements IValidator {
    private static Storage instance;
    private final String storageLocation;

    private Storage() throws IOException, NoSuchFieldException, ConfigurationException {
        Configuration configuration = Configuration.getInstance();
        configuration.loadPropertyFile("config.properties");
        storageLocation = configuration.getValue("storage");
    }

    public static Storage getInstance() throws IOException, NoSuchFieldException, ConfigurationException {
        if (Storage.instance == null) {
            Storage.instance = new Storage();
        }
        return Storage.instance;
    }

    Path createFile(String subfolder, String filename, String runId) throws IOException {
        String pathId = String.format("%s%s/", storageLocation, runId);
        if (!Files.exists(Paths.get(pathId))) {
            Files.createDirectory(Paths.get(pathId));
        }

        String path = String.format("%s%s/%s/", storageLocation, runId, subfolder);
        if (!Files.exists(Paths.get(path))) {
            Files.createDirectory(Paths.get(path));
        }

        String filePath = String.format("%s%s/%s/%s", storageLocation, runId, subfolder, filename);
        Path newFilePath = Paths.get(filePath);
        Files.createFile(newFilePath);
        return newFilePath;
    }

    long writeContent(IItem item, String content) throws IOException {
        FileChannel writeChannel;
        long result;
        try (ReadableByteChannel readChannel = Channels.newChannel(new URL(content).openStream())) {
            try (FileOutputStream fileOS = new FileOutputStream(item.getPath().toFile())) {
                writeChannel = fileOS.getChannel();
                result = writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
                writeChannel.close();
            }
        }
        return result;
    }

    byte[] readContent(IItem item) throws IOException {
        Path filePath = item.getPath();
        return Files.readAllBytes(filePath);
    }

    Path writeMetadata(IItem item) throws ParserConfigurationException, TransformerException {
        Path filePath = item.getPath();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        doc.appendChild(doc.adoptNode(item.getMetadata().cloneNode(true)));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult file = new StreamResult(filePath.toFile());
        transformer.transform(source, file);

        return filePath;
    }

    Element readMetadata(IItem item) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(item.getPath().toFile());
        doc.getDocumentElement().normalize();
        return doc.getDocumentElement();
    }

    public Template getMailTemplate(String templateName) throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration();
        cfg.setClassForTemplateLoading(this.getClass(), "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg.getTemplate(templateName);
    }

    @Override
    public boolean isReady() throws IOException {
        if (instance == null) {
            return false;
        }

        if(!Files.isWritable(Paths.get(storageLocation))) {
            throw new IOException(String.format("Storage %s not writable.", storageLocation));
        }
        return true;
    }
}
