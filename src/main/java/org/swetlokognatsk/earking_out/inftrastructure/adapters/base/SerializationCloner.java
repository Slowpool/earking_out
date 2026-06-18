package org.swetlokognatsk.earking_out.inftrastructure.adapters.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

public final class SerializationCloner implements ObjectCloner {

    public <T> T clone(final T object) {
        var out = new ByteArrayOutputStream();
        try {
            var objOut = new ObjectOutputStream(out);
            objOut.writeObject(object);

            var in = new ByteArrayInputStream(out.toByteArray());
            var objIn = new ObjectInputStream(in);
            return (T) objIn.readObject();
        } catch (Exception e) {
            throw new IllegalArgumentException("clonning error", e);
        }

    }

}
