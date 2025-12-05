package nl.bos.fop;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;

import static org.apache.xmlgraphics.util.MimeConstants.MIME_PDF;

public class PdfGenerator {

    private PdfGenerator() {
        //Hide the implicit public one!
    }

    /**
     * @param xmlData      "<data><to>John</to><from>Doe</from></data>"
     * @param fopConfig  "http://localhost:8080/fop.xconf | c:\\Temp\\fop.xconf"
     * @param foXslt     "http://localhost:8080/xsltInput.fo | c:\\Temp\\xsltInput.fo"
     * @return a Base64 String representation of the PDF content
     */
    public static String generate(String xmlData, String fopConfig, String foXslt) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
            // Step 1: Construct a FopFactory by specifying a reference to the configuration file

            FopFactory fopFactory;
            if(isValidUrl(fopConfig)) {
                fopFactory = FopFactory.newInstance(new URI(fopConfig));
            } else {
                fopFactory = FopFactory.newInstance(new File(fopConfig));
            }

            // Step 3: Construct fop with desired output format
            Fop fop = fopFactory.newFop(MIME_PDF, out);

            // Step 4: Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Source srcXslt;
            if(isValidUrl(fopConfig)) {
                srcXslt = new StreamSource(foXslt);
            } else {
                srcXslt = new StreamSource(new File(foXslt));
            }
            Transformer transformer = factory.newTransformer(srcXslt);

            // Step 5: Setup input and output for XSLT transformation
            Source srcXml = new StreamSource(new StringReader(xmlData));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Step 6: Start XSLT transformation and FOP processing
            transformer.transform(srcXml, res);

            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (URISyntaxException | TransformerException | IOException | SAXException e) {
            throw new PdfGeneratorException(e.getMessage(), e);
        }
    }

    private static boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
