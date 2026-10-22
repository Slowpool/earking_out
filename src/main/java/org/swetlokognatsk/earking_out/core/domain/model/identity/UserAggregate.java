package org.swetlokognatsk.earking_out.core.domain.model.identity;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthIdentity;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthProviders;
import lombok.NonNull;
import static java.util.Objects.requireNonNull;
import java.util.Set;

public final class UserAggregate extends AggregateRoot<UserId> {

    // TODO add created_on
    private final UserUuid uuid;
    private String name;
    private Languages lang;

    private Set<OauthIdentity> oauthIdentities;

    public UserUuid getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull final String name) {
        this.name = name;
    }

    public Languages getLang() {
        return lang;
    }

    public String getPictureUrl() {
        return oauthIdentities.size() == 0
                ? null
                // yet taking randomnly
                : oauthIdentities.iterator()
                        .next()
                        .getPictureUrl();
    }

    public UserAggregate(final UserId id, final UserUuid uuid, final String name, @NonNull final Languages lang, @NonNull final Set<OauthIdentity> oauthIdentities) {
        super(id);

        this.uuid = requireNonNull(uuid);
        this.name = requireNonNull(name);
        this.lang = lang;
        this.oauthIdentities = oauthIdentities;
    }

    public void changeLanguage(final Languages newLanguage) {
        this.lang = newLanguage;
    }

    public boolean hasOauthIdentity(final OauthProviders provider) {
        return oauthIdentities.stream()
                .anyMatch(oauthIdentity -> oauthIdentity.provider.equals(provider));
    }
}
