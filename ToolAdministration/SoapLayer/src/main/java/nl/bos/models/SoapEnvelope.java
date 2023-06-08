package nl.bos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Envelope")
public abstract class SoapEnvelope {

    @JacksonXmlProperty(localName = "Header")
    private SoapHeader soapHeader;

    @JsonIgnoreType
    static class SoapHeader {
    }
}
