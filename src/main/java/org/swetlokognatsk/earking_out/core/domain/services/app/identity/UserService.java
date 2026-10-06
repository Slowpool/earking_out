package org.swetlokognatsk.earking_out.core.domain.services.app.identity;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserId createGuestUser() {
        var guest = userRepository.createAndSaveGuestUser("Guest_");
        return guest.getId();
    }
}
