package org.swetlokognatsk.earking_out.test_fakes;

public class Singleton {
    public static final Singleton singleton = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return singleton;
    }
}
