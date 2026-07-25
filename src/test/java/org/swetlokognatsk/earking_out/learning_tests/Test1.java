package org.swetlokognatsk.earking_out.learning_tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Test1 {
    public static int value = 0;

    @Test
    public void doSomething() {
        Test2.value = 1;
        assertEquals(0, Test1.value);
        assertEquals(1, Test2.value);
    }

}
