package nl.bos.http;

import com.cordys.documentstore.oauth.token.OAuthClient;
import com.cordys.documentstore.oauth.token.OAuthClientFactory;
import com.eibus.security.identity.Identity;
import com.eibus.util.logger.CordysLogger;
import com.eibus.util.system.EIBProperties;
import com.opentext.applicationconnector.httpconnector.IHTTPObject;
import com.opentext.applicationconnector.httpconnector.config.IParameter;
import com.opentext.applicationconnector.httpconnector.config.IServerConnection;
import com.opentext.applicationconnector.httpconnector.exception.HandlerException;
import com.opentext.applicationconnector.httpconnector.impl.RestRequestHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class MyRestRequestHandler extends RestRequestHandler {
    private static final CordysLogger LOG = CordysLogger.getCordysLogger(MyRestRequestHandler.class);
    private static final Properties props;

    static {
        File config = Paths.get(EIBProperties.getInstallDir(), "nl", "bos", "config", "config.properties").toFile();
        if(LOG.isDebugEnabled()) {
            LOG.debug(String.format("Path=%s", config.getPath()));
        }
        props = new Properties();
        try {
            props.load(new FileInputStream(config));
        } catch (IOException e) {
            throw new RuntimeException("Failed reading the property file", e);
        }
    }

    @Override
    public IHTTPObject process(int requestNode, IServerConnection connection, Identity identity) throws HandlerException {
        if(LOG.isDebugEnabled()) {
            LOG.debug(String.format("MyRestRequestHandler.process() for user: %s", identity.getAuthenticatedUserCN()));
        }

        String clientId = props.getProperty("nl.bos.http.client_id");
        String clientSecret = props.getProperty("nl.bos.http.client_secret");

        if(LOG.isDebugEnabled()) {
            LOG.debug(String.format("prop1=%s, prop2=%s", clientId, clientSecret));
        }

        Map<String, IParameter> parameters = connection.getParameters();
        for (Map.Entry<String, IParameter> parameter : parameters.entrySet()) {
            if(LOG.isDebugEnabled()) {
                LOG.debug(String.format("key: %s; name: %s; value: %s", parameter.getKey(), parameter.getValue().getName(), parameter.getValue().getValue()));
            }
        }

        OAuthClient oAuthClient = OAuthClientFactory.getInstance();
        String authToken = oAuthClient.getAuthToken(clientId, clientSecret, identity);

        IHTTPObject process = super.process(requestNode, connection, identity);
        HttpURLConnection httpUrlConnection = process.getHttpUrlConnection();
        httpUrlConnection.setRequestProperty("Authorization", String.format("Bearer %s", authToken));
        httpUrlConnection.setRequestProperty("Content-Type","application/json");

        return process;
    }
}