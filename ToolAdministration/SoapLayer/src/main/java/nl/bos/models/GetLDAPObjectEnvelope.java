package nl.bos.models;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class GetLDAPObjectEnvelope extends SoapEnvelope {

    @JacksonXmlProperty(localName = "Body")
    private Body body;

    public Body getBody() {
        return body;
    }

    public static class Body {
        @JacksonXmlProperty(localName = "GetLDAPObjectResponse")
        private GetLDAPObjectResponse getLDAPObjectResponse;

        public GetLDAPObjectResponse getGetLDAPObjectResponse() {
            return getLDAPObjectResponse;
        }

        public static class GetLDAPObjectResponse {
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
                        @JacksonXmlElementWrapper(localName = "busmethodsignature")
                        private List busMethodSignature;
                        @JacksonXmlProperty
                        private List<String> cn;
                        @JacksonXmlProperty(localName = "objectclass")
                        private List<String> objectClass;
                        @JacksonXmlElementWrapper(localName = "busmethodimplementation")
                        private List busMethodImplementation;
                    }
                }
            }
        }
    }
}
