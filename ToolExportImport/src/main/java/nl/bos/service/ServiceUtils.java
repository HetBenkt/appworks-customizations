package nl.bos.service;

import nl.bos.message.Log;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class ServiceUtils {

    private ServiceUtils() {
        //Hide the implicit public one
    }

    public static void addAuthenticationHeader(SOAPEnvelope soapEnvelope, String token) throws SOAPException {
        SOAPHeader soapHeader = soapEnvelope.getHeader();
        QName otAuthenticationName = new QName("urn:api.bpm.opentext.com", "OTAuthentication");
        SOAPHeaderElement otAuthenticationElement = soapHeader.addHeaderElement(otAuthenticationName);
        SOAPElement authenticationTokenElement = otAuthenticationElement.addChildElement("AuthenticationToken");
        authenticationTokenElement.setTextContent(token);
    }

    public static SOAPMessage callService(HttpURLConnection connection, SOAPMessage soapMessage) throws IOException, SOAPException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        byte[] byteArray = out.toByteArray();

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(byteArray);

        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String result = bufferedReader.readLine();

        InputStream is = new ByteArrayInputStream(result.getBytes());
        SOAPMessage response = MessageFactory.newInstance().createMessage(null, is);

        out.close();
        outputStream.close();
        bufferedReader.close();
        inputStreamReader.close();

        return response;
    }

    public static String soapMessageToString(SOAPMessage message) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            message.writeTo(byteArrayOutputStream);
        } catch (SOAPException | IOException e) {
            Log.getInstance().send(e.getMessage(), Level.SEVERE);
        }
        return byteArrayOutputStream.toString();
    }

    public static HttpURLConnection createConnection(String urlString, String method, String contentType) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", contentType);
        return connection;
    }
}
