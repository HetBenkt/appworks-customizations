package nl.bos.message;

import org.junit.Assert;
import org.junit.Test;

public class ConfirmTest {

    @Test
    public void getInstance() {
        Assert.assertNotNull(Confirm.getInstance());
    }

    @Test
    public void isReady() {
        Assert.assertTrue(Confirm.getInstance().isReady());
    }

}