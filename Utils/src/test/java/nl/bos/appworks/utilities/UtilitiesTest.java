package nl.bos.appworks.utilities;

import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.XMLException;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilitiesTest {

    private final static String ACTUAL = "1080027f7a5dca1eeb2d6a5d1169782a3.110000.0000Hello worldP1M2024-02-17Ztrue/home/appworks_tips/app/entityRestService/Items(080027f7a5dca1eeb2d6a5d1169782a3.1)/Image?iv=1708019810834&name=case_logotest0012024-02-15Zt1case-12F8B156E1037111E6E9CB0FBF3334FBBF.22024-02-15T17:56:54Z2024-02-15T17:56:53Z2F8B156E1037111E6E9CB0FBF3334FBBF.22F8B156E1037111E6E9CB0FBF3334FBBF.2080027f7-a5dc-a1ee-b30a-e694dbd817c5Draft080027f7-a5dc-a1ee-b30a-a1e060de082dDraftDraftNew";

    private final static String XML_INPUT = """
            <?xml version='1.0' encoding='UTF-8'?>
            <note>
                <to>Tove</to>
                <from>Jani</from>
                <heading>Reminder</heading>
                <body>Don't forget me this weekend!</body>
            </note>
            """;

    @Test
    void testGetHelloString() {
        Assertions.assertThat(Utilities.getHelloString("World")).isEqualTo("Hello World");

        XPath xpath = XPath.getXPathInstance("nl.bos.appworks.utilities.Utilities.getHelloString(\"World\")");
        XPathResult result = xpath.evaluate(null);
        Assertions.assertThat(result.getStringResult()).isEqualTo("Hello World");
    }

    @Test
    void testMatchesString() {
        Assertions.assertThat(Utilities.matches("a*b", "aaaaab")).isTrue();
        Assertions.assertThat(Utilities.matches(".", ACTUAL)).isFalse();
        Assertions.assertThat(Utilities.matches("", ACTUAL)).isFalse();
        Assertions.assertThat(Utilities.matches(".*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*Draft.*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*Hello world.*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*test001.*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*test00[1-9].*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*true.*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*t\\d.*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*P\\dM.*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*2024(-02)?-17Z.*", ACTUAL)).isTrue();
        Assertions.assertThat(Utilities.matches(".*2024-(\\d{2}-)?17Z.*", ACTUAL)).isTrue();

        XPath xpath = XPath.getXPathInstance("nl.bos.appworks.utilities.Utilities.matches(\"a*b\", \"aaaaab\")");
        XPathResult result = xpath.evaluate(null);
        Assertions.assertThat(result.getBooleanResult()).isTrue();
    }

    @Test
    void testMatchesXml() throws XMLException {
        int xmlInput = new Document().load(XML_INPUT.getBytes());
        Assertions.assertThat(Utilities.matches(".*Reminder.*", xmlInput)).isTrue();

        XPath xpath = XPath.getXPathInstance("nl.bos.appworks.utilities.Utilities.matches(\".*Jani.*\", "+xmlInput+")");
        XPathResult result = xpath.evaluate(null);
        Assertions.assertThat(result.getBooleanResult()).isTrue();
    }

    @Test
    void testMatchesNodeSet() throws XMLException {
        int xmlInput = new Document().load(XML_INPUT.getBytes());
        XPath expression = XPath.getXPathInstance("//note/node()");
        NodeSet nodeSet = expression.selectNodeSet(xmlInput);
        Assertions.assertThat(Utilities.matches(".*Reminder.*", nodeSet)).isTrue();
    }
}