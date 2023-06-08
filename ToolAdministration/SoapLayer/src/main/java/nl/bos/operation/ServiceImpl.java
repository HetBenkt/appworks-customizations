package nl.bos.operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nl.bos.Utils;
import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.exception.GeneralAppException;
import nl.bos.models.SoapEnvelope;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.logging.Logger;

public class ServiceImpl implements Service {

    private static final Authentication authentication = AuthenticationImpl.getInstance();
    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());
    private final Configuration config = AppWorksPlatformImpl.getInstance().getConfig();

    //TODO soapEnvelope should have a dynamic thing; like the "dn" in GetLDAPObject call!?
    @Override
    public SoapEnvelope call(String body, Class<? extends SoapEnvelope> soapEnvelope) throws JsonProcessingException {
        String responseBody;
        String samlArtifactId;

        CloseableHttpClient client = HttpClients.createDefault();
        String gatewayUrl = config.getProperties().getProperty("gateway_url");

        try {
            samlArtifactId = authentication.getSamlArtifactId();
            String url = String.format("%s?SAMLart=%s", gatewayUrl, samlArtifactId);
            HttpPost httpPost = new HttpPost(url);

            StringEntity entity = new StringEntity(body);
            httpPost.setEntity(entity);

            httpPost.setHeader("Content-type", ContentType.TEXT_XML.getMimeType());
            CloseableHttpResponse response = client.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            String msg = MessageFormat.format("Status: {0}; Body: {1}", response.getStatusLine().getStatusCode(), Utils.formatXml(responseBody));
            logger.info(msg);

            client.close();
        } catch (IOException | TransformerException | ParserConfigurationException | SAXException e) {
            throw new GeneralAppException(e);
        }

        return new XmlMapper().readValue(responseBody, soapEnvelope);
    }
}
