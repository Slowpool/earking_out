package org.swetlokognatsk.earking_out.core.domain.services.app.identity;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
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

    public void changeLanguage(final String newLanguage) {
        var user = repository.get(userResolver.getCurrentUserId());
        user.changeLanguage(newLanguage);
        repository.save(user);
    }
}
