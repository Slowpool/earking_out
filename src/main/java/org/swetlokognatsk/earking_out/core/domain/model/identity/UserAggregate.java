package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import lombok.NonNull;
import static java.util.Objects.requireNonNull;

public final class UserAggregate extends AggregateRoot<UserId> {

    private final UserUuid uuid;
    private final String name;
    private Languages lang;

    public UserUuid getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Languages getLang() {
        return lang;
    }

    public UserAggregate(final UserId id, final UserUuid uuid, final String name, @NonNull final Languages lang) {
        super(id);

        this.uuid = requireNonNull(uuid);
        this.name = requireNonNull(name);
        this.lang = lang;
    }

    public void changeLanguage(final Languages newLanguage) {
        this.lang = newLanguage;
    }
}
