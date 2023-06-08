package nl.bos.mapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "properties")
public class PropertiesModel implements Serializable {
    @XmlElement(name = "property")
    List<PropertyModel> propertyModel;

    @Override
    public String toString() {
        return String.format("properties [ propertyModel=%s ]", propertyModel);
    }
}
