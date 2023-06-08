package nl.bos.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import nl.bos.Utils;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@WireMockTest
abstract class AbstractWireMockTest {
    static final WireMockServer wireMockAppWorksServer = new WireMockServer(); //default is http://localhost:8080
    static final WireMockServer wireMockOtdsServer = new WireMockServer(options().port(8181)); //Better use dynamicPort(), but we read a props-file!

    @BeforeAll
    static void isSystemUp() throws IOException {
        wireMockAppWorksServer.start();
        wireMockOtdsServer.start();

        wireMockAppWorksServer.stubFor(
                get(urlEqualTo("/home/system/app/mp/health/ready"))
                        .willReturn(aResponse()
                                .withBody(TestMockData.jsonResponseHealth)));
        wireMockAppWorksServer.stubFor(
                post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withRequestBody(equalToXml(TestMockData.soapRequestSamlToken))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/xml")
                                .withBody(TestMockData.soapResponseSamlToken)));

        Configuration config = new ConfigurationImpl("config_mock.properties");
        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance(config);
        Assumptions.assumeThat(awp.ping()).isTrue();

        wireMockAppWorksServer.verify(getRequestedFor(urlEqualTo("/home/system/app/mp/health/ready")));
    }

    @AfterAll
    static void shutdownWireMockAndCleanData() throws IOException {
        if (Utils.artifactFileExists()) {
            Utils.deleteArtifactFile();
        }
        wireMockAppWorksServer.stop();
        wireMockOtdsServer.stop();
    }
}
