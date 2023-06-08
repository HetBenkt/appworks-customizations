package nl.bos.item;

import org.w3c.dom.Node;

import java.nio.file.Path;

public class Content implements IItem {
    @Override
    public Path getPath() {
        return null;
    }

    @Override
    public void setPath(Path path) {
        //TODO Needs implementation
    }

    @Override
    public Node getMetadata() {
        return null;
    }

    @Override
    public void setMetadata(Node metadataExpected) {
        //TODO Needs implementation
    }
}
