package nl.bos;

import nl.bos.action.IProcessor;
import nl.bos.item.Entity;
import nl.bos.item.IItem;
import org.apache.commons.configuration.ConfigurationException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;

public class Processor implements IProcessor {

    @Override
    public void execute(List<IItem> entities, String runId) throws NoSuchFieldException, IOException, ConfigurationException, TransformerException, ParserConfigurationException {
        Storage storage = Storage.getInstance();

        for (IItem item : entities) {
            Entity entity = (Entity) item;
            Path path = storage.createFile(entity.getType(), entity.getItemId() + ".xml", runId);
            entity.setPath(path);
            storage.writeMetadata(entity);
            List<String> contents = entity.getContents();
            if (contents != null) {
                for (int i = 0; i < contents.size(); i++) {
                    String content = contents.get(i);
                    URLConnection urlConnection = new URL(content).openConnection();
                    String contentType = urlConnection.getContentType();
                    Path contentFile = storage.createFile(entity.getType(), entity.getItemId() + "_" + entity.getContentIds().get(i) + "_content" + getExtension(contentType), runId);
                    entity.setPath(contentFile);
                    storage.writeContent(entity, content);
                }
            }
        }
    }

    private String getExtension(String contentType) {
        switch (contentType) {
            case "text/plain":
                return ".txt";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return ".docx";
            default:
                return "nak";
        }
    }
}
