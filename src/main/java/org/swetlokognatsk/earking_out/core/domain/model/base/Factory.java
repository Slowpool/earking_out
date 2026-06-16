package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.util.ArrayList;

// TODO how 'bout using Command-like pattern for `repository/factory/aggreagate_constructor` chain? to avoid lengthy params duplications
// TODO use this interface for all factories
public abstract interface Factory<O, DADTO extends DependentAggregatesDTO> {
    abstract O createDefault(final DADTO dependentAggregates);

    abstract O createDeepCopy(final O o);

    default O[] createDeepCopy(final O[] os) {
        var osCopy = new ArrayList<O>(os.length);
        for (int i = 0; i < osCopy.size(); i++) {
            osCopy.add(i, createDeepCopy(os[i]));
        }
        return (O[]) osCopy.toArray();
    }
}
