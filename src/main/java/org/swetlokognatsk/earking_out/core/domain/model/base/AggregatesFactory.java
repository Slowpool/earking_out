package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.lang.reflect.Array;

import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

// TODO how 'bout using Command-like pattern for `repository/factory/aggreagate_constructor` chain? to avoid lengthy params duplications
// TODO use this interface for all factories
// TODO explore Factory/Factory method/Abstract factory
public abstract class AggregatesFactory<O> {
    private final ObjectCloner cloner;

    public AggregatesFactory(final ObjectCloner cloner) {
        this.cloner = cloner;
    }

    public O createDeepCopy(final O o) {
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
