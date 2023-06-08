package nl.bos.integration;

import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AuthenticationIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getSamlToken() throws IOException {
        Authentication authentication = AuthenticationImpl.getInstance();
        authentication.disableOtds();
        Assertions.assertThat(authentication.getSamlArtifactId()).isNotEmpty();
    }

    @Test
    void getSamlTokenFromOtdsToken() throws IOException {
        Authentication authentication = AuthenticationImpl.getInstance();
        authentication.enableOtds();
        Assertions.assertThat(authentication.getSamlArtifactId()).isNotEmpty();
    }
}
