package nl.bos.item;

import org.w3c.dom.Node;

import java.nio.file.Path;

public interface IItem {

    Path getPath();

    void setPath(Path path);

    Node getMetadata();

    void setMetadata(Node metadataExpected);
}
