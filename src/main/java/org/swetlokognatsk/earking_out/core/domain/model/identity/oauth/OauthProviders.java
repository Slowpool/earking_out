package org.swetlokognatsk.earking_out.core.domain.model.identity.oauth;

import java.util.Arrays;

public enum OauthProviders {
    VKID(1), Github(2);

    public final int id;

    private OauthProviders(final int id) {
        this.id = id;
    }

    public static OauthProviders valueOf(final int id) {
        return Arrays.stream(values())
                .filter(provider -> provider.id == id)
                .findFirst()
                .orElseThrow();
    }
}
