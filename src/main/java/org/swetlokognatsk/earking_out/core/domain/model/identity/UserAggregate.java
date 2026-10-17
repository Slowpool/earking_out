package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import lombok.NonNull;
import static java.util.Objects.requireNonNull;

public final class UserAggregate extends AggregateRoot<UserId> {

    private final UserUuid uuid;
    private final String name;
    // TODO use Language value object
    private String lang;

    public String getLang() {
        return lang;
    }
    
    public UserAggregate(final UserId id, final UserUuid uuid, final String name, @NonNull final String lang) {
        super(id);

        this.uuid = requireNonNull(uuid);
        this.name = requireNonNull(name);
        this.lang = lang;
    }

    // TODO use Language value object
    public void changeLanguage(final String newLanguage) {
        this.lang = newLanguage;
    }
}
