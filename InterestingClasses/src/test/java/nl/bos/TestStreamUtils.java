package nl.bos;

import com.opentext.cordys.entityCore.elements.historyElement.HistoryLog;
import com.opentext.cordys.entityCore.utilities.stream.StreamFactory;
import com.opentext.cordys.entityCore.utilities.stream.StreamUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

class TestStreamUtils {

    private final static String ACTUAL = "<History><CD e=\"080027f7a5dca1eea7e061c773fcb579\" i=\"080027f7a5dca1eea7e061c773fcb579.1\" t=\"project-1\" v=\"ca15687458733325a316150d5a63e1ed\" x=\"080027f7a5dca1eea7e061c773fcb579.1.\"/></History>";

    @Test
    void doNoneTest() {
        byte[] compress = StreamUtils.compress(ACTUAL, getStreamCompressionFactory(HistoryLog.CompressionType.None));
        byte[] decompress = StreamUtils.decompress(compress, getStreamDecompressionFactory(HistoryLog.CompressionType.None));
        String expected = new String(decompress, StandardCharsets.UTF_8);
        Assertions.assertThat(ACTUAL).isEqualTo(expected);
    }

    @Test
    void doDeflateTest() {
        byte[] compress = StreamUtils.compress(ACTUAL, getStreamCompressionFactory(HistoryLog.CompressionType.Deflate));
        byte[] decompress = StreamUtils.decompress(compress, getStreamDecompressionFactory(HistoryLog.CompressionType.Deflate));
        String expected = new String(decompress, StandardCharsets.UTF_8);
        Assertions.assertThat(ACTUAL).isEqualTo(expected);
    }

    @Test
    void doGZipTest() {
        byte[] compress = StreamUtils.compress(ACTUAL, getStreamCompressionFactory(HistoryLog.CompressionType.GZip));
        byte[] decompress = StreamUtils.decompress(compress, getStreamDecompressionFactory(HistoryLog.CompressionType.GZip));
        String expected = new String(decompress, StandardCharsets.UTF_8);
        Assertions.assertThat(ACTUAL).isEqualTo(expected);
    }

    private static Function<InputStream, InputStream> getStreamDecompressionFactory(HistoryLog.CompressionType compressionType) {
        return compressionType == HistoryLog.CompressionType.Deflate ? StreamFactory::newInflaterInputStream : StreamFactory::newGZIPInputStream;
    }

    private static Function<OutputStream, OutputStream> getStreamCompressionFactory(HistoryLog.CompressionType compressionType) {
        return compressionType == HistoryLog.CompressionType.Deflate ? StreamFactory::newDeflaterOutputStream : StreamFactory::newGZIPOutputStream;
    }
}
