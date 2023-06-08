package nl.bos;

import nl.bos.action.EnumActionTypes;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Configuration.class)
public class ConfigurationTest {

    private Configuration configuration;

    @Before
    public void setUp() {
        configuration = Configuration.getInstance();
    }

    @After
    public void tearDown() {
        configuration.cleanProperties();
    }

    @Test(expected = FileNotFoundException.class)
    public void propertiesLoadFailTest() throws IOException, ConfigurationException {
        configuration.loadPropertyFile("no_config.properties");
    }

    @Test
    public void propertiesLoadTest() throws IOException, ConfigurationException {
        PropertiesConfiguration properties = configuration.loadPropertyFile("config_test.properties");
        Assert.assertNotNull(properties);
    }

    @Test(expected = NoSuchFieldException.class)
    public void propertiesReadFailTest() throws IOException, NoSuchFieldException, ConfigurationException {
        PropertiesConfiguration properties = configuration.loadPropertyFile("config_test.properties");
        Assert.assertNotNull(properties);

        configuration.getValue("service.dummy");
    }

    @Test
    public void propertiesReadTest() throws IOException, NoSuchFieldException, ConfigurationException {
        PropertiesConfiguration properties = configuration.loadPropertyFile("config_test.properties");
        Assert.assertNotNull(properties);

        String actual = configuration.getValue("service.url");
        String expected = "http://<hostname>:<port>/home/<organization>/services";
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void propertiesReadExceptionTest() throws Exception {
        PropertiesConfiguration mockProperties = Mockito.mock(PropertiesConfiguration.class);
        PowerMockito.whenNew(PropertiesConfiguration.class).withAnyArguments().thenReturn(mockProperties);
        Mockito.doThrow(IOException.class).when(mockProperties).setReloadingStrategy(Mockito.any(FileChangedReloadingStrategy.class));

        configuration.loadPropertyFile("config_test.properties");
    }

    @Test
    public void isDoDryRunTest() {
        Assert.assertFalse(configuration.isDoDryRun());
    }

    @Test
    public void setDoDryRunTest() {
        configuration.setDoDryRun(true);
        Assert.assertTrue(configuration.isDoDryRun());
    }

    @Test
    public void isOnlyMetadataTest() {
        Assert.assertFalse(configuration.isOnlyMetadata());
    }

    @Test
    public void setOnlyMetadataTest() {
        configuration.setOnlyMetadata(true);
        Assert.assertTrue(configuration.isOnlyMetadata());
        configuration.setOnlyMetadata(false);
        Assert.assertFalse(configuration.isOnlyMetadata());
    }

    @Test
    public void getModeTest() {
        Assert.assertEquals(EnumActionTypes.EXPORT, configuration.getMode());
    }

    @Test
    public void setModeTest() {
        configuration.setMode(EnumActionTypes.IMPORT);
        Assert.assertEquals(EnumActionTypes.IMPORT, configuration.getMode());
    }

}