package org.swetlokognatsk.earking_out.learning_tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class Test2 {
    public static int value = 0;

    @Test
    public void doSomething() {
        Test1.value = 1;
        assertEquals(1, Test1.value);
        // fails when running the whole package as test in vs code.
        // assertEquals(0, Test2.value);
    }

}
