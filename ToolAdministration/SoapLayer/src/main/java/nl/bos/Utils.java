package nl.bos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {

    private static Path tempFile;

    private Utils() {
        //Utility class; Not to be instantiated.
    }

    public static String formatJson(String uglyJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);
        return gson.toJson(je);
    }

    public static String formatXml(String uglyXmlString) throws TransformerException, ParserConfigurationException, IOException, SAXException {
        InputSource src = new InputSource(new StringReader(uglyXmlString));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        Document document = factory.newDocumentBuilder().parse(src);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        transformerFactory.setAttribute("indent-number", 4);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        Writer out = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(out));
        return out.toString();
    }

    public static void writeToFile(String samlArtifactId) throws IOException {
        tempFile = Files.createTempFile("AppWorksAdmin", "samlArtifactId");
        Files.writeString(tempFile, samlArtifactId, Charset.defaultCharset());
    }

    public static boolean artifactFileExists() {
        if (tempFile == null) {
            return false;
        }
        return Files.exists(tempFile);
    }

    public static void deleteArtifactFile() throws IOException {
        Files.delete(tempFile);
    }

    public static String readFromFile() throws IOException {
        return new String(Files.readAllBytes(tempFile));
    }
}
