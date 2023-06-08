package nl.bos.item;

import org.w3c.dom.Node;

import java.nio.file.Path;
import java.util.List;

public class Entity implements IItem {
    private Path path;
    private int id;
    private Node metadata;
    private String type;
    private String itemId;
    private List<String> contentIds;
    private List<String> contents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Node getMetadata() {
        return metadata;
    }

    public void setMetadata(Node metadata) {
        this.metadata = metadata;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public List<String> getContents() {
        return contents;
    }


    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public List<String> getContentIds() {
        return contentIds;
    }

    public void setContentIds(List<String> contentIds) {
        this.contentIds = contentIds;
    }
}
