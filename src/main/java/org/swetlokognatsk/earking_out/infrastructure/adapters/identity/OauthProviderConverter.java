package org.swetlokognatsk.earking_out.infrastructure.adapters.identity;

import org.swetlokognatsk.earking_out.core.domain.model.identity.oauth.OauthProviders;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OauthProviderConverter implements AttributeConverter<OauthProviders, Integer> {
    public OauthProviders convertToEntityAttribute(final Integer id) {
        return OauthProviders.valueOf(id);
    }

    public Integer convertToDatabaseColumn(final OauthProviders provider) {
        return provider.id;
    }

}
