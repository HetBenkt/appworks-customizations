package nl.bos.ws.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.exception.GeneralAppException;
import nl.bos.models.OtdsResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RestWebServiceOtdsTicket implements SoapWebServiceStrategy {
    private static final Configuration config = AppWorksPlatformImpl.getInstance().getConfig();
    private final String url;
    private static final Logger logger = Logger.getLogger(RestWebServiceOtdsTicket.class.getName());

    public RestWebServiceOtdsTicket(final String url) {
        this.url = url;
    }

    @Override
    public String run() {
        String ticket;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        Map<Object, Object> data = new HashMap<>();
        data.put("userName", config.getProperties().getProperty("username"));
        data.put("password", config.getProperties().getProperty("password"));
        data.put("targetResourceId", config.getProperties().getProperty("targetResourceId"));

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(data));
            httpPost.setEntity(entity);

            httpPost.setHeader("Content-type", ContentType.APPLICATION_JSON.getMimeType());
            CloseableHttpResponse response = client.execute(httpPost);
            String responseJsonBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

            OtdsResponse otdsResponse = objectMapper.readValue(responseJsonBody, OtdsResponse.class);
            ticket = otdsResponse.ticket();

            String msg = MessageFormat.format("Status: {0}; Body: {1}; Ticket: {2}", response.getStatusLine().getStatusCode(), responseJsonBody, ticket);
            logger.info(msg);

            client.close();
        } catch (IOException e) {
            throw new GeneralAppException(e);
        }

        return ticket;
    }
}
