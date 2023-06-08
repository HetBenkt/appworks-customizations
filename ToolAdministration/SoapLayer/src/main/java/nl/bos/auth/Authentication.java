package nl.bos.auth;

import java.io.IOException;

public interface Authentication {
    String getSamlArtifactId() throws IOException;

    void enableOtds();

    void disableOtds();
}
