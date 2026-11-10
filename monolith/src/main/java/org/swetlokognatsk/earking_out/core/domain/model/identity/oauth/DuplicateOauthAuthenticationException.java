package org.swetlokognatsk.earking_out.core.domain.model.identity.oauth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DuplicateOauthAuthenticationException extends Exception {
    public final OauthProviders provider;
}
