package org.swetlokognatsk.earking_out.core.domain.model.base;

import java.io.Serializable;
import java.util.Objects;

// Serializable is infrastructure-level-requirement, that simplifies objects cloning. that compromiss has a lots of advantages over memento pattern that is quite complicated (as i heard): 1. unit tests aren't required because cloning via serialization is already tested by serialization itself. 2. no need to maintain memento. 3. serialization logic works the same for all objects, though idk whether memento requires configuring each object clonning `config` or not
public abstract class Entity<ID> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final ID id;

    public Entity(final ID id) {
        Objects.nonNull(id);

        this.id = id;
    }

    public ID getId() {
        return id;
    }
}
