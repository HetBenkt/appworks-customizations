package nl.bos.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class SoapProcessorsEnvelope extends SoapEnvelope {

    @JacksonXmlProperty(localName = "Body")
    private Body body;

    public Body getBody() {
        return body;
    }

    public static class Body {
        @JacksonXmlProperty(localName = "GetSoapProcessorsResponse")
        private SoapProcessorsResponse soapProcessorsResponse;

        public SoapProcessorsResponse getSoapProcessorsResponse() {
            return soapProcessorsResponse;
        }

        public static class SoapProcessorsResponse {
            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "tuple")
            private List<Tuple> tuples;

            public List<Tuple> getTuples() {
                return tuples;
            }

            private static class Tuple {
                @JacksonXmlProperty(localName = "old")
                private Old old;

                private static class Old {
                    @JacksonXmlProperty(localName = "entry")
                    private Entry entry;

                    @JsonIgnoreProperties
                    private static class Entry {
                        @JsonProperty
                        private String dn;
                        @JsonProperty
                        private String entryUUID;
                        @JacksonXmlProperty(localName = "bussoapprocessorconfiguration")
                        private List<String> busSoapProcessorConfiguration;
                        @JacksonXmlProperty(localName = "computer")
                        private List<String> computer;
                        @JacksonXmlProperty(localName = "description")
                        private List<String> description;
                        @JacksonXmlProperty(localName = "busosprocesshost")
                        private List<String> busOsProcessHost;
                        @JacksonXmlProperty(localName = "automaticstart")
                        private List<String> automaticStart;
                        @JacksonXmlProperty(localName = "cn")
                        private List<String> cn;
                        @JacksonXmlProperty(localName = "objectclass")
                        private List<String> objectClass;
                    }
                }
            }
        }
    }
}
