package nl.bos.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.bos.exception.GeneralAppException;
import nl.bos.integration.TestIntegrationData;
import nl.bos.models.*;
import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceWireMockTest extends AbstractWireMockTest {

    @Test
    void callGetUserDetails() throws JsonProcessingException {
        wireMockAppWorksServer.stubFor(
                post(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetUserDetails))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/xml")
                                .withBody(TestMockData.soapResponseGetUserDetails)));

        Service service = new ServiceImpl();
        SoapEnvelope serviceResponse = service.call(TestIntegrationData.soapRequestGetUserDetails, UserDetailsEnvelope.class);

        wireMockAppWorksServer.verify(
                postRequestedFor(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetUserDetails)));
        Assumptions.assumeThat(serviceResponse).isInstanceOf(UserDetailsEnvelope.class);
        Assumptions.assumeThat(((UserDetailsEnvelope) serviceResponse).getBody().getUserDetailsResponse().getUser()).isNotNull();
    }

    @Test
    void callGetSoapProcessors() throws JsonProcessingException {
        wireMockAppWorksServer.stubFor(
                post(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetSoapProcessors))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/xml")
                                .withBody(TestMockData.soapResponseGetSoapProcessors)));

        Service service = new ServiceImpl();
        SoapEnvelope serviceResponse = service.call(TestIntegrationData.soapRequestGetSoapProcessors, SoapProcessorsEnvelope.class);

        wireMockAppWorksServer.verify(
                postRequestedFor(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetSoapProcessors)));
        Assumptions.assumeThat(serviceResponse).isInstanceOf(SoapProcessorsEnvelope.class);
        Assumptions.assumeThat(((SoapProcessorsEnvelope) serviceResponse).getBody().getSoapProcessorsResponse().getTuples().size()).isEqualTo(2);
    }

    @Test
    void callSearchLDAP() throws JsonProcessingException {
        wireMockAppWorksServer.stubFor(
                post(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestSearchLDAP))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/xml")
                                .withBody(TestMockData.soapResponseSearchLDAP)));

        Service service = new ServiceImpl();
        SoapEnvelope serviceResponse = service.call(TestIntegrationData.soapRequestSearchLDAP, SearchLDAPEnvelope.class);

        wireMockAppWorksServer.verify(
                postRequestedFor(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestSearchLDAP)));
        Assumptions.assumeThat(serviceResponse).isInstanceOf(SearchLDAPEnvelope.class);
        Assumptions.assumeThat(((SearchLDAPEnvelope) serviceResponse).getBody().getSearchLDAPResponse().getTuples().size()).isEqualTo(2);
    }

    @Test
    void callGetLDAPObject() throws JsonProcessingException {
        wireMockAppWorksServer.stubFor(
                post(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetLDAPObject))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/xml")
                                .withBody(TestMockData.soapResponseGetLDAPObject)));

        Service service = new ServiceImpl();
        SoapEnvelope serviceResponse = service.call(TestIntegrationData.soapRequestGetLDAPObject, GetLDAPObjectEnvelope.class);

        wireMockAppWorksServer.verify(
                postRequestedFor(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetLDAPObject)));
        Assumptions.assumeThat(serviceResponse).isInstanceOf(GetLDAPObjectEnvelope.class);
        Assumptions.assumeThat(((GetLDAPObjectEnvelope) serviceResponse).getBody().getGetLDAPObjectResponse().getTuples().size()).isEqualTo(1);
    }

    @Test
    void getServiceFailure() {
        wireMockAppWorksServer.resetMappings();

        Service service = new ServiceImpl();
        Exception exception = assertThrows(GeneralAppException.class, () -> service.call(TestIntegrationData.soapRequestGetSoapProcessors, SoapEnvelope.class));
        Assertions.assertThat(exception.getClass().getSimpleName()).isEqualTo(GeneralAppException.class.getSimpleName());
    }
}