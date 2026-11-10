package org.swetlokognatsk.earking_out.core.domain.services.app.identity;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.identity.Languages;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.DuplicateOauthAuthenticationException;
import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthProviders;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserResolver userResolver;

    public UserId createGuestUser() {
        var guest = repository.createAndSaveGuestUser("Guest_");
        return guest.getId();
    }

    public void changeLanguage(final Languages newLanguage) {
        var user = repository.get(userResolver.getCurrentUserId());
        user.changeLanguage(newLanguage);
        repository.save(user);
    }

    // TODO scenarios:
    // 1. no any user in session - ignored, because it's not natural scenario for app - the user will just get an error (by exception in user repo - user not found). either browser cookies are disabled, either user is hacker or kinda.
    // 2. new user (guest) just used oauth by some provider the first time - attach this oauth data to this guest user, change name, attach avatar
    // 3. technically new user (guest) used oauth provider, and he is has already registered via this provider before - for now, throw away the current guest user, save the id of already registered via oauth user to session
    // 4. user with already attach some oauth provider x decided to also attach oauth provider y - yet not implemented.
    /**
     * @return Optional<UserId> actualUserId - only when the user attached to oauth differs from the current user
     */
    public Optional<UserId> ensureUserIsAttachedToOauth(final OauthProviders provider, final String providerUserId, final String userName, final String pictureUrl) throws DuplicateOauthAuthenticationException {
        var userId = userResolver.getCurrentUserId();
        var user = repository.get(userId);

        UserId actualUserId;
        if (user.hasOauthIdentity(provider)) {
            throw new DuplicateOauthAuthenticationException(provider);
        } else if (repository.hasOauthIdentity(provider, providerUserId)) {
            var oauthIdentity = repository.getOauthIdentity(provider, providerUserId);
            actualUserId = oauthIdentity.userId;
        } else {
            repository.attachOauthIdentity(userId, provider, providerUserId, userName, pictureUrl);
            actualUserId = null;
        }
        return Optional.ofNullable(actualUserId);
    }
}
