package nl.bos.service;

import nl.bos.Configuration;
import nl.bos.item.EItemTypes;
import nl.bos.item.Entity;
import nl.bos.item.IItem;
import nl.bos.item.ItemFactory;
import nl.bos.mapper.Mapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO Write the tests for the Service class!
@RunWith(PowerMockRunner.class)
@PrepareForTest({Service.class, ServiceUtils.class, ResultParser.class, SOAPConnectionFactory.class})
public class ServiceTest {

    private final Mapper mapper = Mapper.getInstance();
    private final Configuration configuration = Configuration.getInstance();
    private final Service service = Service.getInstance();
    private HttpURLConnection httpURLConnection;
    private OutputStream outputStream;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private SOAPConnectionFactory soapConnectionFactory;
    private SOAPConnection soapConnection;
    private SOAPMessage soapResponse;
    private SOAPBody body;
    private NodeList nodelist;
    private Node node;
    private ResultParser resultParser;
    private NodeList entityData;
    private Element entityMetadata;
    private ItemFactory itemFactory;
    private Entity entity;
    private NodeList nodeList;
    private Node nodeTemp;
    private URL obj;
    private HttpURLConnection con;
    private InputStream inputStreamTemp;
    private BufferedReader in;

    @Before
    public void setUp() throws Exception {
        //HTTP requests
        httpURLConnection = PowerMockito.mock(HttpURLConnection.class);
        outputStream = PowerMockito.mock(OutputStream.class);
        inputStream = PowerMockito.mock(InputStream.class);
        inputStreamReader = PowerMockito.mock(InputStreamReader.class);
        bufferedReader = PowerMockito.mock(BufferedReader.class);

        PowerMockito.spy(ServiceUtils.class);
        PowerMockito.doReturn(httpURLConnection).when(ServiceUtils.class);
        ServiceUtils.createConnection(Mockito.anyObject(), Mockito.anyObject(), Mockito.anyObject());
        PowerMockito.when(httpURLConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        PowerMockito.when(httpURLConnection.getOutputStream()).thenReturn(outputStream);
        PowerMockito.when(httpURLConnection.getInputStream()).thenReturn(inputStream);
        PowerMockito.whenNew(InputStreamReader.class).withAnyArguments().thenReturn(inputStreamReader);
        PowerMockito.whenNew(BufferedReader.class).withAnyArguments().thenReturn(bufferedReader);
        PowerMockito.when(bufferedReader.readLine()).thenReturn("{\"token\":\"6F7464735F73657373696F6E5F6B6579\",\"userId\":\"dev01@sb\",\"ticket\":\"*OTDSSSO*AXhBQlJGWXZyY3RYSnR0ZnUyejdMYWV1cXJsTE1mM0FBUWotWmFOMnR1ZjMtQnVYX0NwMGJNNHdEd0gzTDFrMDlpM1ppa1pkWENkRVRLNXZybUQ0M0ZuWVVoR3RxRWlnSVk4ZVpOYkZWbm1HemVGakZvQUxkdXZwNGVRNmUxanRXd29ORTh1TE9sVi1qam5kMmhOY0NmX1Q0ZXI4Z3NjMzBiMzAwTkYzdGltQ1NGTUdLVEg1c2xWNFBuRWx4WmNHN2NSSVd6cm1xdkRMRHNyRzFUWDdSa21xSG5LTVZmOHB2SjQ5c1l3UllHbXJkcVlmOTlJNGt3dFlQTHFmRVlEelBSSWE3d01RaU42Y2pOV0gxVFN6dDFfSnV1U0l4aU9STVJRajhVOHRsME1HZDU1Mm9Qbmh3LTA1dUdGUW9XYkhoOXp6WFE5eFFRb2wtckFCazY1U05WUXM2TVFsSzd6aklrWVhOaW5iUE1RVnpEaExrd0VVd3BQOW9vAE4ASgAUf9FGllJov1ZK6kc7tBf6-kUg7S0AELR2GirjuGEeN09RF50TDbwAIATbZCLk9_Um-8gRZduTp1niF58T-IJfBQ8smGFdq-yTAAA*\",\"resourceID\":null,\"failureReason\":null,\"passwordExpirationTime\":0,\"continuation\":false,\"continuationContext\":null,\"continuationData\":null}");

        //SAML artifact
        soapConnectionFactory = PowerMockito.mock(SOAPConnectionFactory.class);
        soapConnection = PowerMockito.mock(SOAPConnection.class);
        soapResponse = PowerMockito.mock(SOAPMessage.class);
        body = PowerMockito.mock(SOAPBody.class);
        nodelist = PowerMockito.mock(NodeList.class);
        node = PowerMockito.mock(Node.class);

        PowerMockito.spy(SOAPConnectionFactory.class);
        PowerMockito.doReturn(soapConnectionFactory).when(SOAPConnectionFactory.class);
        SOAPConnectionFactory.newInstance();
        PowerMockito.when(soapConnectionFactory.createConnection()).thenReturn(soapConnection);
        PowerMockito.when(soapConnection.call(Mockito.anyObject(), Mockito.anyObject())).thenReturn(soapResponse);
        PowerMockito.when(soapResponse.getSOAPBody()).thenReturn(body);
        PowerMockito.when(body.getElementsByTagName(Mockito.anyString())).thenReturn(nodelist);
        PowerMockito.when(nodelist.item(Mockito.anyInt())).thenReturn(node);
        PowerMockito.when(node.getTextContent()).thenReturn("DummyAssertionArtifactValue");

        mapper.isReady();
        configuration.loadPropertyFile("config.properties");
        configuration.isReady();
        service.isReady();
    }

    @After
    public void tearDown() {
        //Clean up the mess
    }

    @Test
    public void getInstance() {
        Assert.assertNotNull(Service.getInstance());
    }

    @Test
    public void isReady() throws NoSuchFieldException, IOException {
        Assert.assertTrue(service.isReady());
    }

    @Test
    public void getEntityInstances() throws Exception {
        resultParser = PowerMockito.mock(ResultParser.class);
        entityData = PowerMockito.mock(NodeList.class);
        entityMetadata = PowerMockito.mock(Element.class);
        itemFactory = PowerMockito.mock(ItemFactory.class);
        entity = PowerMockito.mock(Entity.class);
        nodeList = PowerMockito.mock(NodeList.class);
        nodeTemp = PowerMockito.mock(Node.class);
        obj = PowerMockito.mock(URL.class);
        con = PowerMockito.mock(HttpURLConnection.class);
        inputStreamTemp = PowerMockito.mock(InputStream.class);
        in = PowerMockito.mock(BufferedReader.class);

        PowerMockito.whenNew(ItemFactory.class).withNoArguments().thenReturn(itemFactory);
        PowerMockito.spy(ResultParser.class);
        PowerMockito.doReturn(resultParser).when(ResultParser.class);
        ResultParser.getInstance();
        PowerMockito.when(resultParser.getEntityData(Mockito.anyString())).thenReturn(entityData);
        PowerMockito.when(entityData.getLength()).thenReturn(2);
        PowerMockito.when(entityData.item(Mockito.anyInt())).thenReturn(entityMetadata);
        PowerMockito.when(itemFactory.getItem(EItemTypes.ENTITY)).thenReturn(entity);
        PowerMockito.when(entityMetadata.getElementsByTagName(Mockito.anyString())).thenReturn(nodeList);
        PowerMockito.when(nodeList.item(Mockito.anyInt())).thenReturn(nodeTemp);
        PowerMockito.when(nodeTemp.getTextContent()).thenReturn("1001", "1001");
        PowerMockito.when(entity.getItemId()).thenReturn("1001.1");
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(obj);
        PowerMockito.when(obj.openConnection()).thenReturn(con);
        PowerMockito.when(con.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        PowerMockito.whenNew(BufferedReader.class).withAnyArguments().thenReturn(in);
        PowerMockito.when(con.getInputStream()).thenReturn(inputStreamTemp);
        PowerMockito.when(in.readLine()).thenReturn("empty"
                , "{item: {Contents: [{ContentTemplate: {ContentType: FILE}, Identity: {\"ItemId1\":\"1001.1\"}, File: {StorageTicket: \"dummy\"} }]} }", null
                , "{item: {Contents: [{ContentTemplate: {ContentType: FILE}, Identity: {\"ItemId1\":\"1001.1\"}, File: {StorageTicket: \"dummy\"} }]} }", null
                , "empty"
                , "{item: {Contents: [{ContentTemplate: {ContentType: FILE}, Identity: {\"ItemId1\":\"1001.1\"}, File: {StorageTicket: \"dummy\"} }]} }", null
                , "{item: {Contents: [{ContentTemplate: {ContentType: FILE}, Identity: {\"ItemId1\":\"1001.1\"}, File: {StorageTicket: \"dummy\"} }]} }", null);
        PowerMockito.when(entity.getContentIds()).thenReturn(Collections.singletonList("1001.1"));

        List<IItem> entityInstances = service.getEntityInstances();

        Assert.assertEquals(4, entityInstances.size());
    }

    @Test
    public void getContent() throws SOAPException, NoSuchFieldException {
        Entity item = new Entity();
        List<String> contentIds = new ArrayList<>();
        contentIds.add("000d3aa8a828a1eaac282d7abbfc1256.1.327681");
        item.setContentIds(contentIds);

        List<String> contents = service.getContents(item);
        Assert.assertEquals(1, contents.size());
    }

    @Test
    public void getSAMLart() throws SOAPException, NoSuchFieldException {
        String result = service.getSAMLart();
        Assert.assertEquals("DummyAssertionArtifactValue", result);
    }
}