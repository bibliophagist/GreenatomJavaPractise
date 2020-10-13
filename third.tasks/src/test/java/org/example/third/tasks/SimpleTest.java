package org.example.third.tasks;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void simpleTest() {
        int x = 2;
        int y = 23;

        Assert.assertEquals(46, x * y);
        Assert.assertEquals(25, x + y);
    }

    @Test(expected = ArithmeticException.class)
    public void exceptionTet() {
        int i = 0;
        int j = 1 / i;
    }
}
