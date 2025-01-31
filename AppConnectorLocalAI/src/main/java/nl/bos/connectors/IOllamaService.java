package nl.bos.connectors;

public interface IOllamaService {

    boolean ping();
    String generateSync(String message);
}
