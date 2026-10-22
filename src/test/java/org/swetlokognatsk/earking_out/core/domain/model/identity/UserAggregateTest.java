package org.swetlokognatsk.earking_out.core.domain.model.identity;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthIdentity;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthIdentityId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthProviders;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.HashSet;

public class UserAggregateTest {
    private static final String ANY_PROVIDER_USER_ID = "1937";

    // public createUser() {
    //     var aggregate = new UserAggregate(new UserId(1), UserUuid.random());

    // }

    private UserAggregate createAnyUser(final Set<OauthIdentity> oauthIdentities) {
        return new UserAggregate(new UserId(1), UserUuid.random(), "john", Languages.en, oauthIdentities);
    }

    private OauthIdentity createAnyOauthIdentity(final OauthProviders oauthProvider) {
        // yep, setting oauth id = 1, user id = 1 always
        return new OauthIdentity(new OauthIdentityId(1), new UserId(1), oauthProvider, ANY_PROVIDER_USER_ID, "", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void positiveHasOauthIdentityForAllProviders() {
        var oauthIdentities = new HashSet<OauthIdentity>();
        for (var oauthProvider : OauthProviders.values()) {
            oauthIdentities.add(createAnyOauthIdentity(oauthProvider));
        }

        var user = createAnyUser(oauthIdentities);

        for (var oauthProvider : OauthProviders.values()) {
            assertTrue(user.hasOauthIdentity(oauthProvider));
        }
    }

    @Test
    public void negativeHasOauthIdentityForAllProviders() {
        var oauthIdentities = new HashSet<OauthIdentity>();

        var user = createAnyUser(oauthIdentities);

        // yep, it does not cover all provider_ids, because it would be overkill
        for (var oauthProvider : OauthProviders.values()) {
            assertFalse(user.hasOauthIdentity(oauthProvider));
        }
    }

    @Test
    public void differentProviderButTheSameProviderUserId() {
        var oauthIdentities = new HashSet<OauthIdentity>();
        oauthIdentities.add(createAnyOauthIdentity(OauthProviders.Github));

        var user = createAnyUser(oauthIdentities);

        assertFalse(user.hasOauthIdentity(OauthProviders.VKID));
    }
}
