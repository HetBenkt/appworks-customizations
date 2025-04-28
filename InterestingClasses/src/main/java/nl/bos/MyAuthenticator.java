package nl.bos;

import com.eibus.security.authentication.AuthenticationException;
import com.eibus.security.authentication.Authenticator;
import com.eibus.security.authentication.InvalidAuthenticatorException;
import com.eibus.security.identity.Credentials;
import com.eibus.security.identity.InvalidCredentialsException;
import com.eibus.util.logger.CordysLogger;

import java.util.Properties;

public class MyAuthenticator implements Authenticator {

    private final CordysLogger logger = CordysLogger.getCordysLogger(this.getClass());

    public MyAuthenticator() {
        super();
        logger.debug("Constructor:MyAuthenticator");
    }

    @Override
    public void open(Properties properties) throws InvalidAuthenticatorException {
       logger.debug("Method:open");
        properties.forEach((key, value) ->
                logger.debug(key + ": " + value)
        );
    }

    @Override
    public boolean authenticate(Credentials credentials) throws InvalidCredentialsException, AuthenticationException {
        logger.debug("Method:authenticate");
        return true;
    }

    @Override
    public void close() {
        logger.debug("Method:close");
    }
}
