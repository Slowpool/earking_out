package org.swetlokognatsk.earking_out.app.web.services.converters;

import org.springframework.core.convert.converter.Converter;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;

@WebComponent
public class StringToUserIdConverter implements Converter<String, UserId> {

    public UserId convert(final String source) {
        return new UserId(Integer.valueOf(source));
    }
}
