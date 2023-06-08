package nl.bos.service;

import nl.bos.Configuration;
import nl.bos.item.EItemTypes;
import nl.bos.item.Entity;
import nl.bos.item.IItem;
import nl.bos.item.ItemFactory;
import nl.bos.mapper.EntityModel;
import nl.bos.mapper.Mapper;
import nl.bos.mapper.MapperModel;
import nl.bos.message.Log;
import nl.bos.validation.IValidator;
import org.apache.http.client.HttpResponseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Service implements IValidator {
    private final Configuration configuration = Configuration.getInstance();
    private static Service instance;
    private String ticket = "";

    private Service() {
        //Hide for Singleton
    }

    public static Service getInstance() {
        if (instance == null)
            instance = new Service();
        return instance;
    }

    @Override
    public boolean isReady() throws NoSuchFieldException, IOException {
        if (instance == null) {
            return false;
        }

        urlReachable(configuration.getValue("health.url"));
        ticket = validateCredentials();
        urlReachable(configuration.getValue("service.url"));
        //TODO Check the soap.url and a soap.url for an entity name

        return true;
    }

    private void urlReachable(String serviceUrl) throws IOException {
        HttpURLConnection connection = ServiceUtils.createConnection(serviceUrl, "GET", "");
        int responseCode = connection.getResponseCode();
        Log.getInstance().send("ResponseCode " + responseCode);
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new HttpResponseException(responseCode, responseCode + " on " + serviceUrl);
        }
    }

    private String validateCredentials() throws NoSuchFieldException, IOException {
        return getOTDSTicket();
    }

    /**
     * {"userName": "", "password": "","targetResourceId":""}
     *
     * @return The ticket returned from OTDS
     * @throws NoSuchFieldException When the field is not found in the configuration
     * @throws IOException          When the json data write to the body fails
     */
    private String getOTDSTicket() throws NoSuchFieldException, IOException {
        HttpURLConnection connection = ServiceUtils.createConnection(configuration.getValue("otds.auth_url"), "POST", "application/json");
        OutputStream outputStream = connection.getOutputStream();
        String jsonData = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", configuration.getValue("service.user_name"), configuration.getValue("service.password"));
        outputStream.write(requireNonNull(jsonData).getBytes());

        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        JSONObject jsonObject = new JSONObject(reader.readLine());
        Log.getInstance().send(jsonObject.toString());

        outputStream.close();
        inputStream.close();
        inputStreamReader.close();
        reader.close();
        connection.disconnect();

        return jsonObject.getString("ticket");
    }

    public List<IItem> getEntityInstances() throws XPathExpressionException, SOAPException, NoSuchFieldException, IOException {
        List<IItem> result = new ArrayList<>();
        MapperModel mapperModel = Mapper.getInstance().getMapperModel();
        List<EntityModel> entityModels = mapperModel.getEntityModel();

        ItemFactory itemFactory = new ItemFactory();
        for (EntityModel entityModel : entityModels) {
            String entityName = entityModel.getName();
            GetEntity getEntity = new GetEntity(ticket, entityName);
            getEntity.execute(getEntity.buildSoapMessage());

            NodeList entityData = ResultParser.getInstance().getEntityData(String.format("//*[local-name() = 'getResponse']/*[local-name() = '%s']", entityName));

            for (int i = 0; i < entityData.getLength(); i++) {
                Element entityMetadata = (Element) entityData.item(i);
                Entity entity = (Entity) itemFactory.getItem(EItemTypes.ENTITY);
                entity.setId(Integer.parseInt(entityMetadata.getElementsByTagName("Id").item(0).getTextContent()));
                entity.setItemId(entityMetadata.getElementsByTagName("ItemId").item(0).getTextContent());
                entity.setMetadata(entityMetadata);
                List<String> contentIds = getContentIds(entity);
                if (!contentIds.isEmpty()) {
                    entity.setContentIds(contentIds);
                    entity.setContents(getContents(entity));
                }
                entity.setType(entityName);
                result.add(entity);
            }
        }

        return result;
    }

    private List<String> getContentIds(IItem entity) throws SOAPException, NoSuchFieldException, IOException {
        List<String> result = new ArrayList<>();

        String samLart = getSAMLart();
        String restURL = configuration.getValue("rest.url");
        String url = String.format("%s/Items(%s)?include=All,Usage&language=en-US&SAMLart=%s", restURL, ((Entity) entity).getItemId(), samLart);
        Log.getInstance().send("url " + url);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            StringBuilder response = getStringBuilder(con);
            JSONObject jsonObject = new JSONObject(String.valueOf(response));
            try {
                JSONArray jsonArray = jsonObject.getJSONObject("item").getJSONArray("Contents");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject contentObject = (JSONObject) jsonArray.get(i);
                    if (contentObject.getJSONObject("ContentTemplate").getString("ContentType").equals("FILE")) {
                        String contentId = contentObject.getJSONObject("Identity").getString("ItemId1");
                        String storageTicket = contentObject.getJSONObject("File").getString("StorageTicket");
                        Log.getInstance().send(storageTicket);
                        result.add(contentId);
                    }
                }
            } catch (JSONException e) {
                Log.getInstance().send("No content found for entity");
                return result;
            }
        } else {
            Log.getInstance().send("GET request not worked");
        }

        return result;
    }

    public List<String> getContents(IItem entity) throws NoSuchFieldException, SOAPException {
        List<String> result = new ArrayList<>();

        String samLart = getSAMLart();
        String restURL = configuration.getValue("rest.url");
        List<String> contentIds = ((Entity) entity).getContentIds();
        for (String contentId : contentIds) {
            String url = String.format("%s/Items(%s)/ContentStream?forceDownload=true&SAMLart=%s", restURL, contentId, samLart);
            Log.getInstance().send("url " + url);
            result.add(url);
        }

        return result;
    }

    private StringBuilder getStringBuilder(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        Log.getInstance().send(response.toString());
        in.close();

        return response;
    }

    public String getSAMLart() throws SOAPException, NoSuchFieldException {
        ISoapMessage getSAMLArtifact = new GetSAMLArtifact(ticket);
        SOAPMessage soapMessage = getSAMLArtifact.buildSoapMessage();
        return callSoapWebService(soapMessage);
    }

    private String callSoapWebService(SOAPMessage soapMessage) throws SOAPException, NoSuchFieldException {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        SOAPMessage soapResponse = soapConnection.call(soapMessage, configuration.getValue("soap.url"));

        SOAPBody body = soapResponse.getSOAPBody();

        String assertionArtifactValue = body.getElementsByTagName("samlp:AssertionArtifact").item(0).getTextContent();

        soapConnection.close();
        return assertionArtifactValue;
    }
}
