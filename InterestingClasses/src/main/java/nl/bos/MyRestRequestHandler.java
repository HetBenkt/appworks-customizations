package nl.bos;

import com.eibus.security.identity.Identity;
import com.opentext.applicationconnector.httpconnector.IHTTPObject;
import com.opentext.applicationconnector.httpconnector.config.IServerConnection;
import com.opentext.applicationconnector.httpconnector.exception.HandlerException;
import com.opentext.applicationconnector.httpconnector.impl.RestRequestHandler;

public class MyRestRequestHandler extends RestRequestHandler {
    @Override
    public IHTTPObject process(int requestNode, IServerConnection connection, Identity identity) throws HandlerException {
        connection.getUsername();
        connection.getPassword();
        return super.process(requestNode, connection, identity);
    }
}
