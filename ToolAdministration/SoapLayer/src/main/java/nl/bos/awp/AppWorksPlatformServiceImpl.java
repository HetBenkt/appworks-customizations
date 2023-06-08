package nl.bos.awp;

import nl.bos.Utils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class AppWorksPlatformServiceImpl implements AppWorksPlatformService {
    private final String healthUrl;
    private static final Logger logger = Logger.getLogger(AppWorksPlatformServiceImpl.class.getName());

    public AppWorksPlatformServiceImpl(final String healthUrl) {
        this.healthUrl = healthUrl;
    }

    @Override
    public boolean ping() {
        boolean result;
        int timeout = 1_000;

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout).build();

        try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            final HttpGet httpGet = new HttpGet(healthUrl);
            httpGet.setHeader("Content-type", ContentType.APPLICATION_JSON.getMimeType());
            CloseableHttpResponse response = client.execute(httpGet);
            String responseJsonBody = Utils.formatJson(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            logger.info(responseJsonBody);
            result = true;
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
}
