package nl.bos.mock;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPException;
import nl.bos.Utils;
import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import nl.bos.ws.strategy.SoapWebServiceToken;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationMockTest {
    private MockedStatic<SOAPConnectionFactory> soapConnectionFactoryMock;
    private MockedStatic<HttpClients> httpClientsMock;
    @Mock
    private SOAPConnectionFactory soapConnectionFactoryInstanceMock;
    @Mock
    private CloseableHttpClient closeableHttpClientMock;
    @Mock
    private SOAPConnection soapConnectionMock;
    @Mock
    private CloseableHttpResponse closeableHttpResponseMock;
    @Mock
    private StatusLine statusLineMock;

    @BeforeAll
    static void isSystemUp() throws IOException {
        Configuration config = new ConfigurationImpl("config_mock.properties");
        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance(config);
        Assumptions.assumeThat(awp.ping()).isFalse();
    }

    private void initSoap() throws SOAPException, IOException {
        soapConnectionFactoryMock = mockStatic(SOAPConnectionFactory.class);

        soapConnectionFactoryMock.when(SOAPConnectionFactory::newInstance).
                thenReturn(soapConnectionFactoryInstanceMock);
        if(!Utils.artifactFileExists()) {
            when(soapConnectionFactoryInstanceMock.createConnection()).
                    thenReturn(soapConnectionMock);
            when(soapConnectionMock.call(any(), any())).
                    thenReturn(MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(TestMockData.soapResponseSamlToken.getBytes())));
        }
    }

    private void initRest() throws IOException {
        httpClientsMock = mockStatic(HttpClients.class);

        httpClientsMock.when(HttpClients::createDefault).
                thenReturn(closeableHttpClientMock);
        when(closeableHttpClientMock.execute(any())).
                thenReturn(closeableHttpResponseMock);
        when(closeableHttpResponseMock.getEntity()).
                thenReturn(new StringEntity(TestMockData.jsonResponseOtdsAuthentication, StandardCharsets.UTF_8));
        when(closeableHttpResponseMock.getStatusLine()).
                thenReturn(statusLineMock);
        when(statusLineMock.getStatusCode()).
                thenReturn(200);
    }

    @BeforeEach
    void cleanData() throws IOException {
        if (Utils.artifactFileExists()) {
            Utils.deleteArtifactFile();
        }
    }

    @Test
    void getSamlToken() throws SOAPException, IOException {
        initSoap();

        Authentication authentication = AuthenticationImpl.getInstance();
        authentication.disableOtds();

        String samlArtifactId;
        samlArtifactId = authentication.getSamlArtifactId();
        verify(soapConnectionMock).call(any(), any());
        Assertions.assertThat(samlArtifactId).isNotEmpty();

        soapConnectionFactoryMock.close();
    }

    @Test
    void getSamlTokenFromOtdsToken() throws SOAPException, IOException {
        initRest();
        initSoap();

        Authentication authentication = AuthenticationImpl.getInstance();
        authentication.enableOtds();

        String samlArtifactId = authentication.getSamlArtifactId();
        verify(closeableHttpClientMock).execute(any());
        verify(soapConnectionMock).call(any(), any());
        Assertions.assertThat(samlArtifactId).isNotEmpty();

        httpClientsMock.close();
        soapConnectionFactoryMock.close();
    }

    @Test
    void unsupportedMethodOnParentClass() {
        SoapWebServiceToken soapWebServiceStrategy = new SoapWebServiceToken("");
        Exception exception = assertThrows(UnsupportedOperationException.class, soapWebServiceStrategy::run);
        Assertions.assertThat(exception.getClass().getSimpleName()).isEqualTo(UnsupportedOperationException.class.getSimpleName());
    }
}
