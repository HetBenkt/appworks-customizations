package nl.bos.fop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Base64;

class PdfGeneratorTest {
    private final static String TEST_RESOURCES = "src/test/resources";
    private final static String XML_DATA = """
                <?xml version="1.0" encoding="UTF-16" ?>
                     <doc>
                     	<head>
                     		<title>Sample</title>
                     	</head>
                     	<body>
                     		<p>Hello World!</p>
                     		<p>This is the first<b>SampleDoc</b>
                     		</p>
                     	</body>
                     </doc>
                """;

    @Test
    void generate() throws IOException {
        String absolutePath = new File(TEST_RESOURCES).getAbsolutePath();
        String base64 = PdfGenerator.generate(XML_DATA, absolutePath + "/" + "fop.xconf", absolutePath + "/" + "input.xslt");
        Assertions.assertThat(base64).isBase64();

        System.out.println(base64);
        try (OutputStream stream = new FileOutputStream(absolutePath + "/" + "output.pdf")) {
            stream.write(Base64.getDecoder().decode(base64));
        }
    }
}