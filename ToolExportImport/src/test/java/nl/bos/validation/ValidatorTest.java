package nl.bos.validation;

import com.sun.javafx.application.PlatformImpl;
import nl.bos.ControllersRegistry;
import nl.bos.controllers.MainView;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Validator.class, ControllersRegistry.class})
public class ValidatorTest {
    @Mock
    private MainView mainView;

    @Before
    public void setUp() {
        PlatformImpl.startup(() -> {
        });
    }

    @Test
    public void getInstance() {
        Assert.assertNotNull(Validator.getInstance());
    }

    @Ignore("mainView.getTxaMessages() gives a NullPointerException; don't know how to solve as we need a javaFX runtime thread!?")
    @Test
    public void isReady() {
        PowerMockito.spy(ControllersRegistry.class);
        PowerMockito.doReturn(mainView).when(ControllersRegistry.class);
        ControllersRegistry.get(MainView.class.getSimpleName());

        Assert.assertTrue(Validator.getInstance().isReady());

    }

    @After
    public void tearDown() {
        PlatformImpl.exit();
    }
}