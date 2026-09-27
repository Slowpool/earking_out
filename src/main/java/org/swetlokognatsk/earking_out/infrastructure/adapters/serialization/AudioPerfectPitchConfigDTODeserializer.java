package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

public final class AudioPerfectPitchConfigDTODeserializer extends StdDeserializer<AudioPerfectPitchConfigDTO> {

    public AudioPerfectPitchConfigDTODeserializer() {
        super(AudioPerfectPitchConfigDTO.class);
    }
    
    @Override
    public AudioPerfectPitchConfigDTO deserialize(final JsonParser parser, final DeserializationContext ctxt) {
        // TODO AudioPerfectPitchConfigDTODeserializer, share the logic with aggregate deserializer somehow
        return null;
    }
}
