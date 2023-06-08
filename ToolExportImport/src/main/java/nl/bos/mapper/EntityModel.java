package nl.bos.mapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "entity")
public class EntityModel implements Serializable {
    @XmlElement(name = "name")
    String name;
    @XmlElement(name = "can_have_content")
    boolean canHaveContent;
    @XmlElement(name = "properties")
    PropertiesModel propertiesModel;

    @Override
    public String toString() {
        return String.format("entity [ name=%s, can_have_content=%s, propertiesModel=%s ]", name, canHaveContent, propertiesModel);
    }

    public String getName() {
        return name;
    }

    public boolean isCanHaveContent() {
        return canHaveContent;
    }

    public PropertiesModel getPropertiesModel() {
        return propertiesModel;
    }
}
