package nl.bos.appworks.utilities;

import com.cordys.security.encryption.impl.AESGCMAlgorithm;
import com.cordys.security.encryption.impl.EncryptionAlgorithm;
import com.eibus.util.UserProfile;
import com.eibus.xml.nom.Document;
import com.eibus.xml.nom.XMLException;
import com.eibus.xml.xpath.NodeSet;
import com.eibus.xml.xpath.XPath;
import com.eibus.xml.xpath.XPathResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

class UtilitiesTest {

    private final static String ACTUAL = "1080027f7a5dca1eeb2d6a5d1169782a3.110000.0000Hello worldP1M2024-02-17Ztrue/home/appworks_tips/app/entityRestService/Items(080027f7a5dca1eeb2d6a5d1169782a3.1)/Image?iv=1708019810834&name=case_logotest0012024-02-15Zt1case-12F8B156E1037111E6E9CB0FBF3334FBBF.22024-02-15T17:56:54Z2024-02-15T17:56:53Z2F8B156E1037111E6E9CB0FBF3334FBBF.22F8B156E1037111E6E9CB0FBF3334FBBF.2080027f7-a5dc-a1ee-b30a-e694dbd817c5Draft080027f7-a5dc-a1ee-b30a-a1e060de082dDraftDraftNew";
    private final static String PASSWORD = "test123";
    private final static String USERNAME = "";
    private final static String PASSWORD_ENCRYPTED = "e0pBVkEtQUVTL0dDTS9Ob1BhZGRpbmd9MlYvZ/A7hJZcnwyLbhN1YtzsOvAzjGKxrySnaI9t1uQF\r\n4VvllCNMCeRYldMx4/r0";

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

        XPath xpath = XPath.getXPathInstance("nl.bos.appworks.utilities.Utilities.matches(\".*Jani.*\", " + xmlInput + ")");
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

    @Test
    void testEncodeDecode() throws NoSuchAlgorithmException, IOException {
        String encode = Base64.getMimeEncoder().encodeToString(PASSWORD.getBytes(StandardCharsets.UTF_8));
        String decode = new String(Base64.getMimeDecoder().decode(encode), StandardCharsets.UTF_8);
        Assertions.assertThat(decode).isEqualTo(PASSWORD);
    }

    @Test
    void testEncryptDecryptApi() throws NoSuchAlgorithmException, IOException {
        String encryptAndEncode = UserProfile.encryptAndEncode(USERNAME, PASSWORD);
        if (UserProfile.isEncrypted(encryptAndEncode)) {
            String decodeAndDecrypt = UserProfile.decodeAndDecrypt(USERNAME, encryptAndEncode);
            Assertions.assertThat(decodeAndDecrypt).isEqualTo(PASSWORD);
        }
    }

    @Test
    void testEncryptDecryptDirect() throws NoSuchAlgorithmException, IOException {
        EncryptionAlgorithm encryptionAlgorithm = new AESGCMAlgorithm();
        byte[] encrypt = encryptionAlgorithm.encrypt(USERNAME.getBytes(StandardCharsets.UTF_8), PASSWORD.getBytes(StandardCharsets.UTF_8));
        byte[] decrypt = encryptionAlgorithm.decrypt(USERNAME.getBytes(StandardCharsets.UTF_8), encrypt);
        Assertions.assertThat(new String(decrypt, StandardCharsets.UTF_8)).isEqualTo(PASSWORD);
    }

    @Test
    @Disabled("Tag mismatch")
    void testDecryptOnly() throws NoSuchAlgorithmException, IOException {
        if (UserProfile.isEncrypted(PASSWORD_ENCRYPTED)) {
            String decodeAndDecrypt = UserProfile.decodeAndDecrypt(USERNAME, PASSWORD_ENCRYPTED);
            Assertions.assertThat(decodeAndDecrypt).isEqualTo(PASSWORD);
        }
    }

    @Test
    void testEncryptDecryptExample() throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String ALGORITHM = "AES/GCM/NoPadding";
        int AES_KEY_SIZE = 256;
        int GCM_NONCE_LENGTH = 16;
        int GCM_TAG_LENGTH = 16;

        // Generate a ivData
        byte[] ivData = new byte[GCM_NONCE_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivData);

        // Generate a new AES symKey
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE);
        SecretKey symKey = keyGen.generateKey();

        // Example plaintext
        byte[] plaintextBytes = PASSWORD.getBytes(StandardCharsets.UTF_8);

        // Encrypt the plaintext
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, ivData);
        cipher.init(Cipher.ENCRYPT_MODE, symKey, gcmSpec);
        byte[] ciphertext = cipher.doFinal(plaintextBytes);

        System.out.println("Ciphertext: " + new String(ciphertext, StandardCharsets.UTF_8));
        // Print ciphertext in Base64 encoding
        System.out.println("Ciphertext (Encoded): " + Base64.getMimeEncoder().encodeToString(ciphertext));

        // Decrypt the ciphertext
        cipher.init(Cipher.DECRYPT_MODE, symKey, gcmSpec);
        byte[] decryptedBytes = cipher.doFinal(ciphertext);

        // Print decrypted plaintext
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);
        System.out.println("Decrypted text: " + decryptedText);

        Assertions.assertThat(decryptedText).isEqualTo(PASSWORD);
    }
}

