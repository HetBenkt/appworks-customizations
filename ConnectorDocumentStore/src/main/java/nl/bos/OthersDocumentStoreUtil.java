package nl.bos;

import com.cordys.documentstore.Document;
import com.cordys.documentstore.exceptions.DocumentStoreException;
import com.cordys.documentstore.messages.Messages;
import com.cordys.documentstore.util.DocumentStoreUtil;
import com.eibus.util.logger.CordysLogger;
import com.eibus.xml.nom.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Base64;
import java.util.Calendar;

public class OthersDocumentStoreUtil {
    private static final CordysLogger LOGGER = CordysLogger.getCordysLogger(OthersDocumentStoreUtil.class);

    public static Document createDocumentFromOthersContent(String url, boolean shouldFetchContent, String fetchSetting) throws DocumentStoreException {
        Document doc = new Document();
        int othersContentNode;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Creating document object from 'others' store...");
        }

        String id = url.substring(url.lastIndexOf("AppWorksTipsAppWorks") + 20);

        doc.setName(id);
        doc.setId(id);
        String storageURL = url.replace("uploadcontent", "storage");
        doc.setDocumentURL(storageURL);
        doc.setFolderUrl(url.substring(0, url.lastIndexOf('/')).replace("uploadcontent", "storage"));

        doc.setMimeType("text/plain");
        doc.setLastModified(Calendar.getInstance());
        if (shouldFetchContent) {
            othersContentNode = loadContent(doc, fetchSetting);
            if (othersContentNode != 0) {
                Node.delete(othersContentNode);
            }
        }
        return doc;
    }

    private static int loadContent(Document doc, String fetchSetting) throws DocumentStoreException {
        String documentContent = "";
        boolean isContentInFile = DocumentStoreUtil.isContentInFile(fetchSetting);
        if (isContentInFile) {
            doc.setContentStreamUrl("/" + doc.getDocumentURL());
            //read
            String content = readContentFromURL(doc.getContentStreamUrl(), true);

            //write
            FileChannel channel;
            try (RandomAccessFile stream = new RandomAccessFile(doc.getContentStreamUrl().replace("storage", "downloadcontent").replace("AppWorksTipsAppWorks", ""), "rw")) {
                channel = stream.getChannel();
                byte[] decode = Base64.getDecoder().decode(content.getBytes());
                ByteBuffer buffer = ByteBuffer.allocate(decode.length);
                buffer.put(decode);
                buffer.flip();
                channel.write(buffer);
            } catch (IOException e) {
                throw new DocumentStoreException(e);
            }

        } else {
            doc.setContent(documentContent.getBytes());
        }
        doc.setContentInFile(isContentInFile);
        doc.setFileSize(documentContent.getBytes().length);
        return 0;
    }

    public static void createOthersDocumentContent(Document doc) throws DocumentStoreException {
        //read
        String content = readContentFromURL(doc.getContentStreamUrl(), true);

        //write
        FileChannel channel;
        String newPath = doc.getContentStreamUrl().replace("uploadcontent", "storage");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("write content to %s", newPath));
        }
        try (RandomAccessFile stream = new RandomAccessFile(newPath, "rw")) {
            channel = stream.getChannel();
            byte[] decode = Base64.getDecoder().decode(content.getBytes());
            ByteBuffer buffer = ByteBuffer.allocate(decode.length);
            buffer.put(decode);
            buffer.flip();
            channel.write(buffer);
        } catch (IOException e) {
            throw new DocumentStoreException(e);
        }
    }

    private static String readContentFromURL(String url, boolean encryptContent) throws DocumentStoreException {
        String content;
        byte[] byteVariable = null;
        try (FileInputStream fis = new FileInputStream(url)) {
            File file = new File(url);
            long length = file.length();
            byte[] lengthInByte = new byte[(int) length];
            fis.read(lengthInByte);
            if (encryptContent) {
                byteVariable = Base64.getEncoder().encode(lengthInByte);
            }
            if (null == byteVariable) {
                throw new DocumentStoreException(Messages.EMPTY_FILE);
            }
            content = new String(byteVariable);
        } catch (IOException e) {
            throw new DocumentStoreException(e);
        }
        return content;
    }
}
