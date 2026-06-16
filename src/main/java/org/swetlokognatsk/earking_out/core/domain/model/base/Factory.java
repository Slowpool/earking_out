package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

// TODO how 'bout using Command-like pattern for `repository/factory/aggreagate_constructor` chain? to avoid lengthy params duplications
// TODO use this interface for all factories
public abstract interface Factory<O, DADTO extends DependentAggregatesDTO> {
    abstract O createDefault(final DADTO dependentAggregates);

    abstract O createDeepCopy(final O o);

    default O[] createDeepCopy(final O[] os) {
        var oType = os.getClass().getComponentType();
        O[] copy = (O[]) Array.newInstance(oType, os.length);
        for (int i = 0; i < os.length; i++) {
            copy[i] = createDeepCopy(os[i]);
        }
        return copy;
    }
}
