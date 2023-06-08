package nl.bos.mapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "mapping")
public class MapperModel implements Serializable {

    @XmlElement(name = "entity")
    List<EntityModel> entityModel;

    @Override
    public String toString() {
        return String.format("mapping [ entityModel=%s ]", entityModel);
    }

    public List<EntityModel> getEntityModel() {
        return entityModel;
    }
}
