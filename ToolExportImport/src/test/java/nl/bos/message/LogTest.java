package nl.bos.message;

import org.junit.Assert;
import org.junit.Test;

public class LogTest {

    @Test
    public void getInstance() {
        Assert.assertNotNull(Log.getInstance());
    }

    @Test
    public void isReady() {
        Assert.assertTrue(Log.getInstance().isReady());
    }
}