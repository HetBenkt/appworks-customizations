package nl.bos.awp;

import nl.bos.config.Configuration;

public interface AppWorksPlatform {
    boolean ping();

    Configuration getConfig();

    void setConfig(Configuration config);
}
