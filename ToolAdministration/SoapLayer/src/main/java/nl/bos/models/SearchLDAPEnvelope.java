package nl.bos.models;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class SearchLDAPEnvelope extends SoapEnvelope {

    @JacksonXmlProperty(localName = "Body")
    private Body body;

    public Body getBody() {
        return body;
    }

    public static class Body {
        @JacksonXmlProperty(localName = "SearchLDAPResponse")
        private SearchLDAPResponse searchLDAPResponse;

        public SearchLDAPResponse getSearchLDAPResponse() {
            return searchLDAPResponse;
        }

        public static class SearchLDAPResponse {
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

                    private static class Entry {
                        @JacksonXmlProperty
                        private String dn;
                        @JacksonXmlProperty
                        private String entryUUID;
                        @JacksonXmlElementWrapper(localName = "bussoapprocessorconfiguration")
                        private List busSoapProcessorConfiguration;
                        @JacksonXmlProperty
                        private List<String> computer;
                        @JacksonXmlProperty
                        private List<String> description;
                        @JacksonXmlProperty(localName = "busosprocesshost")
                        private List<String> busOsProcessHost;
                        @JacksonXmlProperty(localName = "automaticstart")
                        private List<String> automaticStart;
                        @JacksonXmlProperty
                        private List<String> cn;
                        @JacksonXmlProperty(localName = "objectclass")
                        private List<String> objectClass;
                    }
                }
            }
        }
    }
}
