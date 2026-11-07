package org.swetlokognatsk.earking_out.app.web.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.identity.UserService;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.swetlokognatsk.earking_out.SpringProfiles;

@WebMvcTest(IdentityController.class)
@ActiveProfiles(SpringProfiles.WEB)
public class IdentityControllerTest {
    private final static String SIGN_OUT_PATH = "/identity/sign-out";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserResolver userResolver;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserAggregate user;

    @MockitoBean
    private UserService userService;

    private void mockThisUser(final UserAggregate user) {
        Mockito.when(userRepository.get(null))
                .thenReturn(user);
    }

    @Test
    public void signOutBeingLoggedInUser() throws Exception {
        Mockito.when(user.isGuest())
                .thenReturn(false);
        mockThisUser(user);

        mockMvc.perform(post(SIGN_OUT_PATH))
                .andExpect(status()
                        .isOk());
    }

    @Test
    public void signOutBeingGuest() throws Exception {
        Mockito.when(user.isGuest())
                .thenReturn(true);
        mockThisUser(user);

        mockMvc.perform(post(SIGN_OUT_PATH))
                .andExpect(status()
                        .is(401));
    }
}
