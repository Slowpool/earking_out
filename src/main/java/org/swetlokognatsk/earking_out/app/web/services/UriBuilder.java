package org.swetlokognatsk.earking_out.app.web.services;

import java.util.Map;
import org.springframework.web.util.UriComponentsBuilder;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;

@WebComponent
public class UriBuilder {

    public static String buildUri(final String base, final Map<String, String> options, final String hash, final boolean encode) {
        var uri = UriComponentsBuilder.fromUriString(base);
        uri.replaceQuery("");
        for (var option : options.keySet()) {
            uri.queryParam(option, options.get(option));
        }

        if (hash != null) {
            uri.fragment(hash);
        }

        if (encode) {
            uri.encode();
        }

        return uri.build(encode).toUriString();
    }
}
