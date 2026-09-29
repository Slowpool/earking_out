package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public final class UserIdSerializer extends StdSerializer<UserId> {

    public UserIdSerializer() {
        super(UserId.class);
    }

    public void serialize(final UserId userId, final JsonGenerator generator, final SerializationContext ctx) {
        generator.writeNumber(userId.id());
    }

}
