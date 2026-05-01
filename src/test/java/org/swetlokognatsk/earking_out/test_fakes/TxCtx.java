package org.swetlokognatsk.earking_out.test_fakes;

public class TxCtx<T extends Singleton> {
    public int something = 0;
    public T genericVariable;

    public TxCtx() {
        this.genericVariable = (T)Singleton.getInstance();
    }
}

