package nl.bos.integration;

import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import nl.bos.exception.GeneralAppException;

import java.io.IOException;
import java.util.Properties;

public enum TestIntegrationData {
    ;

    private static final String dn;

    static {
        try {
            Configuration config = new ConfigurationImpl("config_integration.properties");
            Properties properties = config.getProperties();
            dn = properties.getProperty("dn");
        } catch (IOException e) {
            throw new GeneralAppException(e);
        }
    }

    public static final String soapRequestGetSoapProcessors = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
              <SOAP:Body>
                <GetSoapProcessors xmlns="http://schemas.cordys.com/1.0/ldap">
                  <dn>$dn</dn>
                  <sort>ascending</sort>
                </GetSoapProcessors>
              </SOAP:Body>
            </SOAP:Envelope>
            """.replace("$dn", dn);

    public static final String soapRequestGetUserDetails = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP:Body>
                    <GetUserDetails xmlns="http://schemas.cordys.com/notification/workflow/1.0">
                    </GetUserDetails>
                </SOAP:Body>
            </SOAP:Envelope>
            """;

    /*
    Retrieve service containers:                &amp;(objectclass=bussoapprocessor)
    Retrieve service containers (filtered):     &amp;(objectclass=bussoapprocessor)(cn=*test*)
    Retrieve service groups:                    &amp;(objectclass=bussoapnode)
    Retrieve service groups (filtered):         &amp;(objectclass=bussoapnode)(cn=*test*)
    Retrieve webservice operations:             &amp;(objectclass=busmethod)
    Retrieve webservice operations (filtered):  &amp;(objectclass=busmethod)(cn=*test*)
    Retrieve webservice interfaces:             &amp;(objectclass=busmethodset)
    Retrieve webservice interfaces (filtered):  &amp;(objectclass=busmethodset)(cn=*test*)
    Retrieve users:                             &amp;(objectclass=busorganizationaluser)
    Retrieve users (filtered):                  &amp;(objectclass=busorganizationaluser)(cn=*test*)
     */
    public static final String soapRequestSearchLDAP = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
              <SOAP:Body>
                <SearchLDAP xmlns="http://schemas.cordys.com/1.0/ldap">
                  <dn>$dn</dn>
                  <scope>2</scope>
                  <filter>&amp;(objectclass=bussoapprocessor)</filter>
                  <sort>ascending</sort>
                  <sortBy></sortBy>
                  <returnValues>false</returnValues>
                  <return></return>
                </SearchLDAP>
              </SOAP:Body>
            </SOAP:Envelope>
            """.replace("$dn", dn);

    public static final String soapRequestGetLDAPObject = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
              <SOAP:Body>
                <GetLDAPObject xmlns="http://schemas.cordys.com/1.0/ldap">
                  <dn>cn=GetMethodSets,cn=Method Set LDAP 1.0,cn=Cordys LDAP Connector,cn=cordys,cn=defaultInst,o=23.1.com</dn>
                </GetLDAPObject>
              </SOAP:Body>
            </SOAP:Envelope>
            """;
}
