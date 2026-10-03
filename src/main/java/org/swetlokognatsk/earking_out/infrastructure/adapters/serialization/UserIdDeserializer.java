package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.core.JsonParser;

public final class UserIdDeserializer extends StdDeserializer<UserId> {

    public UserIdDeserializer() {
        super(UserId.class);
    }

    @Override
    public UserId deserialize(final JsonParser parser, final DeserializationContext ctxt) {
        var userId = parser.getIntValue();
        return new UserId(userId);
    }

}
