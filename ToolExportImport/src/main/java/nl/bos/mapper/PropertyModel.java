package nl.bos.mapper;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "property")
public class PropertyModel {
    @XmlElement(name = "from")
    String from;
    @XmlElement(name = "to")
    String to;

    @Override
    public String toString() {
        return String.format("property [ from=%s, to=%s ]", from, to);
    }
}
