package org.swetlokognatsk.earking_out.core.domain.model.identity.oauth;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.base.Entity;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import lombok.Getter;
import lombok.Setter;

public class OauthIdentity extends Entity<OauthIdentityId> {

    public final UserId userId;
    public final OauthProviders provider;
    public final String providerUserId;
    @Getter
    @Setter
    private String pictureUrl;
    public final LocalDateTime connectedAt;
    @Getter
    @Setter
    // TODO add to migration
    private LocalDateTime lastLoginOn;

    public OauthIdentity(final OauthIdentityId id, final UserId userId, final OauthProviders provider, final String providerUserId, String pictureUrl, final LocalDateTime connectedAt, LocalDateTime lastLoginAt) {
        super(id);
        this.userId = userId;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.pictureUrl = pictureUrl;
        this.connectedAt = connectedAt;
        this.lastLoginOn = lastLoginAt;
    }
}
