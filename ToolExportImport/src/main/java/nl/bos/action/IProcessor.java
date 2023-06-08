package nl.bos.action;

import nl.bos.item.IItem;
import org.apache.commons.configuration.ConfigurationException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface IProcessor {
    void execute(List<IItem> items, String runId) throws NoSuchFieldException, IOException, ConfigurationException, TransformerException, ParserConfigurationException;
}
