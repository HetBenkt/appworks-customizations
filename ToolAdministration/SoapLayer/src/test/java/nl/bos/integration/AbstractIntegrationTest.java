package nl.bos.integration;

import nl.bos.Utils;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

abstract class AbstractIntegrationTest {

    @BeforeAll
    static void isSystemUp() throws IOException {
        Configuration config = new ConfigurationImpl("config_integration.properties");
        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance(config);
        Assumptions.assumeThat(awp.ping()).isTrue();
    }

    @AfterAll
    static void cleanData() throws IOException {
        if (Utils.artifactFileExists()) {
            Utils.deleteArtifactFile();
        }
    }
}