package nl.bos.mock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import nl.bos.Utils;
import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest
class AuthenticationWireMockTest extends AbstractWireMockTest {

    @BeforeEach
    void delete() throws IOException {
        if (Utils.artifactFileExists()) {
            Utils.deleteArtifactFile();
        }
    }

    @Test
    void getSamlToken() throws IOException {
        wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseSamlToken)));

        Authentication authentication = AuthenticationImpl.getInstance();
        authentication.disableOtds();
        String samlArtifactId = authentication.getSamlArtifactId();

        wireMockAppWorksServer.verify(postRequestedFor(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")));
        Assertions.assertThat(samlArtifactId).isNotEmpty();
    }

    @Test
    void getSamlTokenFromOtdsToken() throws IOException {
        wireMockOtdsServer.stubFor(post(urlEqualTo("/otdsws/rest/authentication/credentials")).willReturn(aResponse().withBody(TestMockData.jsonResponseOtdsAuthentication)));
        wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseSamlToken)));

        Authentication authentication = AuthenticationImpl.getInstance();
        authentication.enableOtds();
        String samlArtifactId = authentication.getSamlArtifactId();

        wireMockOtdsServer.verify(postRequestedFor(urlEqualTo("/otdsws/rest/authentication/credentials")));
        wireMockAppWorksServer.verify(postRequestedFor(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")));
        Assertions.assertThat(samlArtifactId).isNotEmpty();
    }
}
