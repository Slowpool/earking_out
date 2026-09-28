package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class PianoKeyNumberSerializer extends StdSerializer<PianoKeyNumber> {

    public PianoKeyNumberSerializer() {
        super(PianoKeyNumber.class);
    }

    public void serialize(final PianoKeyNumber pianoKeyNumber, final JsonGenerator generator, final SerializationContext ctx) {
        generator.writeNumber(pianoKeyNumber.value);
    }
}
