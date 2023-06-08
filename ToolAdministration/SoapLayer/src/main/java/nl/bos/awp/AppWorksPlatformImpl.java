package nl.bos.awp;

import nl.bos.config.Configuration;

public class AppWorksPlatformImpl implements AppWorksPlatform {
    private static AppWorksPlatform appWorksPlatform;

    private Configuration config = null;

    private AppWorksPlatformImpl() {
    }

    public static AppWorksPlatform getInstance(final Configuration config) {
        if (appWorksPlatform == null) {
            appWorksPlatform = new AppWorksPlatformImpl();
        }
        if (config != null) {
            appWorksPlatform.setConfig(config);
        }
        return appWorksPlatform;
    }

    public static AppWorksPlatform getInstance() {
        return getInstance(null);
    }

    @Override
    public boolean ping() {
        AppWorksPlatformService awpService = new AppWorksPlatformServiceImpl(config.getProperties().getProperty("health_url"));
        return awpService.ping();
    }

    @Override
    public Configuration getConfig() {
        return config;
    }

    @Override
    public void setConfig(Configuration config) {
        this.config = config;
    }
}
