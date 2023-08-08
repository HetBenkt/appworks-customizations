package nl.bos;

import com.eibus.soap.BodyBlock;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.Node;
import com.eibus.xml.nom.XMLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NodeTest {

    private final static String PAYLOAD = "the payload!!";
    private final static String TAG = "payload";

    @Test
    void doSetPayloadTest() throws XMLException {
        Document document = new Document();
        int element = document.load("<publishResponse xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns=\"cs\"/>".getBytes());
        BodyBlock response = new BodyBlock(element, null, null);
        System.out.println(Node.writeToString(response.getXMLNode(), false));

        Node.setDataElement(response.getXMLNode(), TAG, PAYLOAD); //this is the call we need!
        System.out.println(Node.writeToString(response.getXMLNode(), false));
        Assertions.assertThat(Node.getData(Node.getElement(response.getXMLNode(), TAG))).isEqualTo(PAYLOAD);
    }
}
