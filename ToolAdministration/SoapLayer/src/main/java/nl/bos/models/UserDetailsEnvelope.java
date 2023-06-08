package nl.bos.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class UserDetailsEnvelope extends SoapEnvelope {

    @JacksonXmlProperty(localName = "Body")
    private Body body;

    public Body getBody() {
        return body;
    }

    public static class Body {
        @JacksonXmlProperty(localName = "GetUserDetailsResponse")
        private UserDetailsResponseResponse userDetailsResponse;

        public UserDetailsResponseResponse getUserDetailsResponse() {
            return userDetailsResponse;
        }

        public static class UserDetailsResponseResponse {
            @JacksonXmlProperty(localName = "User")
            private User user;

            public User getUser() {
                return user;
            }

            private static class User {
                @JacksonXmlProperty(localName = "UserDN")
                private String userDn;
                @JacksonXmlProperty(localName = "UserDisplayName")
                private String userDisplayName;
                @JacksonXmlProperty(localName = "UserId")
                private String userId;
                @JacksonXmlProperty(localName = "CanQueryOrgUnits")
                private String canQueryOrgUnits;
                @JacksonXmlProperty(localName = "ManagerFor")
                private ManagerFor managerFor;

                private static class ManagerFor {
                    @JacksonXmlProperty(localName = "Target")
                    private Target target;

                    private static class Target {
                        @JacksonXmlProperty(localName = "Id")
                        private String id;
                        @JacksonXmlProperty(localName = "Name")
                        private String name;
                        @JacksonXmlProperty(localName = "Type")
                        private String type;
                    }
                }
            }
        }
    }
}
