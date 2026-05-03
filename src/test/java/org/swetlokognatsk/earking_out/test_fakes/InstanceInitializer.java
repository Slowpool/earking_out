package org.swetlokognatsk.earking_out.test_fakes;

public class InstanceInitializer {
    int value;

    public InstanceInitializer() {
        value = 1;
        System.out.println("constructor");
    }

    {
        value = 2;
        System.out.println("instance initializer");
    }
}
