package org.swetlokognatsk.earking_out.core.domain.model.base;

public abstract class Entity<ID> {
    protected final ID id;

    public Entity(final ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }
}
