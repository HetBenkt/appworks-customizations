package nl.bos;

import org.junit.Assert;
import org.junit.Test;

public class ControllersRegistryTest {

    @Test
    public void putAndGet() {
        ControllersRegistry.put("dummyController", new Object());
        Assert.assertNotNull(ControllersRegistry.get("dummyController"));
    }
}