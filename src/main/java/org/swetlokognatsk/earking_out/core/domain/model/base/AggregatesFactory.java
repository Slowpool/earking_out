package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.lang.reflect.Array;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;

// TODO how 'bout using Command-like pattern for `repository/factory/aggreagate_constructor` chain? to avoid lengthy params duplications
// TODO use this interface for all factories
// TODO explore Factory/Factory method/Abstract factory
public abstract class AggregatesFactory<O> {

    public O createDeepCopy(final O o) {
        var cloner = DI.get(ObjectCloner.class);
        return cloner.clone(o);
    }

    public O[] createDeepCopy(final O[] os) {
        var oType = os.getClass().getComponentType();
        O[] copy = (O[]) Array.newInstance(oType, os.length);
        for (int i = 0; i < os.length; i++) {
            copy[i] = createDeepCopy(os[i]);
        }
        return copy;
    }
}
