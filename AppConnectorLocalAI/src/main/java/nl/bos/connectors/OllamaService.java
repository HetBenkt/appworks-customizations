package nl.bos.connectors;

import com.eibus.util.logger.CordysLogger;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.types.OllamaModelType;
import io.github.ollama4j.utils.OptionsBuilder;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class OllamaService implements IOllamaService {
    private final CordysLogger log = CordysLogger.getCordysLogger(this.getClass());
    private OllamaAPI ollamaAPI;

    public OllamaService(final String host, final int port) {
        ollamaAPI = new OllamaAPI();
    }

    @Override
    public boolean ping() {
        boolean isOllamaServerReachable = ollamaAPI.ping();
        if(log.isDebugEnabled()) {
            log.debug(String.format("Is Ollama server running? %s", isOllamaServerReachable));
        }
        return isOllamaServerReachable;
    }

    @Override
    public String generateSync(String message) {
        try {
            return ollamaAPI.generate(OllamaModelType.LLAMA2, message, true, new OptionsBuilder().build()).getResponse();
        } catch (OllamaBaseException | IOException | InterruptedException e) {
            throw new LocalAIException(e);
        }
    }
}
