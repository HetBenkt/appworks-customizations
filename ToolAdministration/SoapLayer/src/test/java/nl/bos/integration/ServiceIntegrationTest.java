package nl.bos.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.bos.models.*;
import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;

class ServiceIntegrationTest extends AbstractIntegrationTest {

    @Test
    void callGetUserDetails() throws JsonProcessingException {
        Service service = new ServiceImpl();
        SoapEnvelope serviceResponse = service.call(TestIntegrationData.soapRequestGetUserDetails, UserDetailsEnvelope.class);
        Assumptions.assumeThat(serviceResponse).isInstanceOf(UserDetailsEnvelope.class);
        Assumptions.assumeThat(((UserDetailsEnvelope) serviceResponse).getBody().getUserDetailsResponse().getUser()).isNotNull();
    }

    @Test
    void callGetSoapProcessors() throws JsonProcessingException {
        Service service = new ServiceImpl();
        SoapEnvelope serviceResponse = service.call(TestIntegrationData.soapRequestGetSoapProcessors, SoapProcessorsEnvelope.class);
        Assumptions.assumeThat(serviceResponse).isInstanceOf(SoapProcessorsEnvelope.class);
        Assumptions.assumeThat(((SoapProcessorsEnvelope) serviceResponse).getBody().getSoapProcessorsResponse().getTuples().size()).isGreaterThan(0);
    }

    @Test
    void callSearchLDAP() throws JsonProcessingException {
        Service service = new ServiceImpl();
        SoapEnvelope serviceResponse = service.call(TestIntegrationData.soapRequestSearchLDAP, SearchLDAPEnvelope.class);
        Assumptions.assumeThat(serviceResponse).isInstanceOf(SearchLDAPEnvelope.class);
        Assumptions.assumeThat(((SearchLDAPEnvelope) serviceResponse).getBody().getSearchLDAPResponse().getTuples().size()).isGreaterThan(0);
    }

    @Test
    void callGetLDAPObject() throws JsonProcessingException {
        Service service = new ServiceImpl();
        SoapEnvelope serviceResponse = service.call(TestIntegrationData.soapRequestGetLDAPObject, GetLDAPObjectEnvelope.class);
        Assumptions.assumeThat(serviceResponse).isInstanceOf(GetLDAPObjectEnvelope.class);
        Assumptions.assumeThat(((GetLDAPObjectEnvelope) serviceResponse).getBody().getGetLDAPObjectResponse().getTuples().size()).isGreaterThan(0);
    }
}