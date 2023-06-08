package nl.bos.auth;

import nl.bos.Utils;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;

import java.io.IOException;

public class AuthenticationImpl implements Authentication {

    private boolean useOtds = false;
    private static Configuration config;
    private static Authentication authentication;

    public static Authentication getInstance() {
        if (authentication == null) {
            authentication = new AuthenticationImpl();
        }
        config = AppWorksPlatformImpl.getInstance().getConfig();
        return authentication;
    }

    private String getToken() {
        AuthenticationService authenticationService = new AuthenticationServiceImpl(config.getProperties().getProperty("gateway_url"));
        return authenticationService.getToken();
    }

    private String getOTDSTicket() {
        AuthenticationService authenticationService = new AuthenticationServiceImpl(config.getProperties().getProperty("otds_url"));
        return authenticationService.getOTDSTicket();
    }

    private String getToken(String otdsTicket) {
        AuthenticationService authenticationService = new AuthenticationServiceImpl(config.getProperties().getProperty("gateway_url"));
        return authenticationService.getToken(otdsTicket);
    }

    @Override
    public String getSamlArtifactId() throws IOException {
        String samlArtifactId;
        if (!Utils.artifactFileExists()) {
            if (useOtds) {
                samlArtifactId = getToken(getOTDSTicket());
            } else {
                samlArtifactId = this.getToken();
            }
            Utils.writeToFile(samlArtifactId);
        } else {
            samlArtifactId = Utils.readFromFile();
        }
        return samlArtifactId;
    }

    @Override
    public void enableOtds() {
        this.useOtds = true;
    }

    @Override
    public void disableOtds() {
        this.useOtds = false;
    }
}
