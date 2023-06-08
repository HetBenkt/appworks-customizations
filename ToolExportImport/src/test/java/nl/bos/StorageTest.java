package nl.bos;

import freemarker.template.Template;
import nl.bos.item.Entity;
import nl.bos.item.IItem;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Storage.class)
public class StorageTest {

    public static final String SUBFOLDER = "test";
    private Storage storage;
    private final static String CONTENT_PDF = "content.pdf";
    private final static String METADATA_XML = "metadata.xml";
    private final List<IItem> items = new ArrayList<>();
    private final String content = "https://file-examples-com.github.io/uploads/2017/10/file-sample_150kB.pdf"; //TODO Mock it!
    private static String runId;

    @BeforeClass
    public static void setupClass() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        runId = dtf.format(LocalDateTime.now());
    }

    @Before
    public void setUp() throws IOException, NoSuchFieldException, ConfigurationException {
        storage = Storage.getInstance();
    }

    @After
    public void tearDown() {
        for (IItem item : items) {
            File file = item.getPath().toFile();
            if (file.exists()) {
                if (file.delete()) {
                    Logger.getLogger(Storage.class.getName()).info(MessageFormat.format("File {0} is deleted", file.getName()));
                }
            }
            if (Paths.get(file.getParent()).toFile().delete()) {
                Logger.getLogger(Storage.class.getName()).info("Entity type directory is deleted");
            }
            if (Paths.get(file.getParent()).getParent().toFile().delete()) {
                Logger.getLogger(Storage.class.getName()).info("Run ID directory is deleted");
            }
        }
    }

    @Test
    public void createFileTest() throws IOException {
        IItem file = new Entity();
        Path path = storage.createFile(SUBFOLDER, CONTENT_PDF, runId);
        file.setPath(path);
        items.add(file);
        assertNotNull(file);
    }

    @Test(expected = IOException.class)
    public void createExistingFileTest() throws IOException {
        IItem file = new Entity();
        Path path = storage.createFile(SUBFOLDER, CONTENT_PDF, runId);
        file.setPath(path);
        items.add(file);
        assertNotNull(file);
        storage.createFile(SUBFOLDER, CONTENT_PDF, runId);
    }

    @Test
    public void writeAndReadContentTest() throws IOException {
        IItem file = new Entity();
        Path path = storage.createFile(SUBFOLDER, CONTENT_PDF, runId);
        file.setPath(path);
        items.add(file);
        assertNotNull(file);

        long pathContent = storage.writeContent(file, content);
        assertNotEquals(0, pathContent);
        byte[] content = storage.readContent(file);

        assertNotEquals(0, content.length);
    }

    private byte[] downloadUrl(String toDownload) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = new URL(toDownload).openStream();

            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();
    }

    @Test(expected = IOException.class)
    public void writeContentFailTest() throws IOException {
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.createFile(Mockito.anyObject())).thenReturn(Paths.get("c:/dummy/dummy.txt"));
        PowerMockito.when(Files.write(Mockito.anyObject(), Mockito.any(byte[].class))).thenThrow(IOException.class);

        IItem file = new Entity();
        Path path = storage.createFile(SUBFOLDER, CONTENT_PDF, runId);
        file.setPath(path);
        items.add(file);
        assertNotNull(file);

        storage.writeContent(file, content);
    }

    @Test(expected = IOException.class)
    public void readContentFailTest() throws IOException {
        PowerMockito.mockStatic(Files.class);
        PowerMockito.when(Files.createFile(Mockito.anyObject())).thenReturn(Paths.get("c:/dummy/dummy.txt"));
        PowerMockito.when(Files.readAllBytes(Mockito.anyObject())).thenThrow(IOException.class);

        IItem file = new Entity();
        Path path = storage.createFile(SUBFOLDER, CONTENT_PDF, runId);
        file.setPath(path);
        items.add(file);
        assertNotNull(file);

        storage.readContent(file);
    }

    @Test
    public void writeAndReadMetadataTest() throws IOException, ParserConfigurationException, TransformerException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        Element metadataExpected = document.createElement("dummy");
        document.appendChild(metadataExpected);

        IItem file = new Entity();
        file.setMetadata(metadataExpected);

        Path path = storage.createFile(SUBFOLDER, METADATA_XML, runId);
        file.setPath(path);
        items.add(file);
        assertNotNull(file);
        Path result = storage.writeMetadata(file);
        assertNotNull(result);
        Element metadataActual = storage.readMetadata(file);
        assertEquals(metadataActual.getTagName(), metadataActual.getTagName());
    }

    @Test
    public void getMailTemplateTest() throws IOException {
        Template mailTemplate = storage.getMailTemplate("emailTemplate.ftl");
        assertNotNull(mailTemplate);
    }

    @Test(expected = FileNotFoundException.class)
    public void getMailTemplateFailTest() throws IOException {
        Template mailTemplate = storage.getMailTemplate("dummy.ftl");
        assertNotNull(mailTemplate);
    }

}