package nl.bos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.*;
import nl.bos.mock.TestMockData;
import nl.bos.models.GetLDAPObjectEnvelope;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.xml.bind.annotation.XmlValue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class JacksonTryOutTest {

    XmlMapper xmlMapper;

    @BeforeEach
    void initXmlMapper() {
        xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    void javaSerializedToXmlString() throws JsonProcessingException {
        String xml = xmlMapper.writeValueAsString(new SimpleBean(1, 2));
        Assertions.assertThat(xml).isNotNull();
        System.out.println(xml);
    }

    @Test
    void javaSerializedToXmlCapitalsString() throws JsonProcessingException {
        String xml = xmlMapper.writeValueAsString(new SimpleBeanForCapitalizedFieldsClass(1, 2));
        Assertions.assertThat(xml).isNotNull();
        System.out.println(xml);
    }

    @Test
    void javaPersonSerializedToXmlString() throws IOException {
        String data = xmlMapper.writeValueAsString(new Person("John", "Doe", List.of("1234544321", "3377465432"), List.of(new Address("Street1", "City1"), new Address("Street2", "City2"))));
        System.out.println(data);
        Assertions.assertThat(data).isNotEmpty();
    }

    @Test
    void javaDeserializedFromXmlString() throws JsonProcessingException {
        String data = """
                <SimpleBean>
                  <x>1</x>
                  <y>2</y>
                </SimpleBean>
                """;
        SimpleBean simpleBean = xmlMapper.readValue(data, SimpleBean.class);
        Assertions.assertThat(simpleBean).isNotNull();
        System.out.println(simpleBean);
        System.out.println(xmlMapper.writeValueAsString(simpleBean));
    }

    @Test
    void javaPersonDeserializedFromXmlString() throws IOException {
        String data = """
                <Person>
                   <firstname>
                   <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" targetNamespace="http://schemas.cordys.com/1.0/ldap" attributeFormDefault="unqualified" elementFormDefault="qualified">
                      <xsd:import namespace="http://schemas.cordys.com/General/1.0/" schemaLocation="http://schemas.cordys.com/CordysSchemas//CordysFaultDetails.xsd" />
                      <xsd:element name="GetMethodSets">
                         <xsd:complexType>
                            <xsd:all>
                               <xsd:element name="labeleduri" type="xsd:string" />
                               <xsd:element name="sort" type="xsd:string" minOccurs="0" />
                               <xsd:element name="dn" type="xsd:string" />
                            </xsd:all>
                         </xsd:complexType>
                      </xsd:element>
                   </xsd:schema>
                   </firstname>
                   <lastname>John<test>Doe</test>&lt;testConvert&gt;Doe&lt;/testConvert&gt;</lastname>
                   <phoneNumbers>
                     <phoneNumbers>1234544321</phoneNumbers>
                     <phoneNumbers>3377465432</phoneNumbers>
                   </phoneNumbers>
                   <addresses>
                     <streetName>Street1</streetName>
                     <city>City1</city>
                   </addresses>
                   <addresses>
                     <streetName>Street2</streetName>
                     <city>City2</city>
                   </addresses>
                </Person>
                """;

        String xsd = data.substring(data.indexOf("<firstname>") + "<firstname>".length(), data.indexOf("</firstname>"));
        String convertedXsd = xsd.replace("<", "&lt;").replace(">", "&gt;");
        StringBuilder builder = new StringBuilder(data);
        builder.replace(data.indexOf("<firstname>"), data.indexOf("</firstname>"), "<firstname>" + convertedXsd.trim());

        Person value = xmlMapper.readValue(String.valueOf(builder), Person.class);
        Assertions.assertThat(value.getFirstname()).startsWith("<xsd:schema");
        Assertions.assertThat(value.getLastname()).isEqualTo("<testConvert>Doe</testConvert>");
        Assertions.assertThat(value.getAddresses()).hasSize(2);
    }

    @Disabled("Requires further implementation")
    @Test
    void javaSoapResponseGetLDAPObjectDeserializedFromXmlString() throws JsonProcessingException {
        xmlMapper.readValue(TestMockData.soapResponseGetLDAPObject, GetLDAPObjectEnvelope.class);
        //TODO implementation
    }

    @Test
    void javaDeserializedFromXmlCapitalsString() throws IOException {
        String data = """
                <SimpleBeanForCapitalizedFields>
                  <X>1</X>
                  <y>2</y>
                </SimpleBeanForCapitalizedFields>
                """;
        SimpleBeanForCapitalizedFieldsClass beanClass = xmlMapper.readValue(data, SimpleBeanForCapitalizedFieldsClass.class);
        System.out.println(beanClass);
        System.out.println(xmlMapper.writeValueAsString(beanClass));
        Assertions.assertThat(beanClass.getX() == 1 && beanClass.getY() == 2).isTrue();

        SimpleBeanForCapitalizedFieldsRecord beanRecord = xmlMapper.readValue(data, SimpleBeanForCapitalizedFieldsRecord.class);
        System.out.println(beanRecord);
        System.out.println(xmlMapper.writeValueAsString(beanRecord));
    }

    @Test
    void javaSerializedToXmlFile() throws IOException {
        //Create file and write data (serialize!)
        Path tempFile = Files.createTempFile("simpleBean", ".xml");
        xmlMapper.writeValue(tempFile.toFile(), new SimpleBean(1, 2));
        System.out.println(tempFile);

        //Read file and print data
        String data = new String(Files.readAllBytes(tempFile));
        Assertions.assertThat(data).isNotNull();
        System.out.println(data);

        //Clean
        if(tempFile.toFile().delete()) {
            System.out.println("File nicely cleaned!");
        }
    }

    @Test
    void javaDeserializedFromXmlFile() throws IOException {
        //Create file and write data (serialize!)
        Path tempFile = Files.createTempFile("simpleBean", ".xml");
        xmlMapper.writeValue(tempFile.toFile(), new SimpleBean(1, 2));
        System.out.println(tempFile);

        //Read file (deserialize!) and print data
        SimpleBean simpleBean = xmlMapper.readValue(tempFile.toFile(), SimpleBean.class);
        System.out.println(simpleBean);
        System.out.println(xmlMapper.writeValueAsString(simpleBean));

        //Assert and clean
        Assertions.assertThat(simpleBean.x).isEqualTo(1);
        Assertions.assertThat(simpleBean.y).isEqualTo(2);
        if(tempFile.toFile().delete()) {
            System.out.println("File nicely cleaned!");
        }
    }

    private record SimpleBean(int x, int y) { }

    private record SimpleBeanForCapitalizedFieldsRecord(@JacksonXmlProperty(localName = "X") int x, int y) { } //@JsonProperty("X") does the same!?

    private static final class SimpleBeanForCapitalizedFieldsClass {
        @JacksonXmlProperty(localName = "X") //@JsonProperty("X") does the same!?
        private int x;
        private int y;

        public SimpleBeanForCapitalizedFieldsClass() {
        }

        public SimpleBeanForCapitalizedFieldsClass(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return String.format("SimpleBeanForCapitalizedFieldsClass[x=%s, y=%s]", x, y);
        }
    }

    private static final class Person {
        //@JacksonXmlText()
        //@XmlValue
        private String firstname;

        @JacksonXmlCData
        private String lastname;
        @JacksonXmlElementWrapper(localName = "phoneNumbers", useWrapping = true) //Now we can leave the 's' in the field name! We can argue about it! :)
        //@JacksonXmlProperty(localName = "phoneNumber") //Not usable on a list; you get duplicate XML entries!?
        private List<String> phoneNumbers = new ArrayList<>();
        @JacksonXmlElementWrapper(useWrapping = false) //Use when List of new object type is used; Not String, but object with fields!
        private List<Address> addresses = new ArrayList<>();

        public Person() {
        }

        public Person(String firstname, String lastname, List<String> phoneNumbers, List<Address> addresses) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.phoneNumbers = phoneNumbers;
            this.addresses = addresses;
        }

        public String getFirstname() {
            return firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public List<String> getPhoneNumbers() {
            return phoneNumbers;
        }

        public List<Address> getAddresses() {
            return addresses;
        }

        @Override
        public String toString() {
            return String.format("Person[firstname=%s, lastname=%s]", firstname, lastname);
        }
    }

    private record Address(String streetName, String city) {
    }
}
