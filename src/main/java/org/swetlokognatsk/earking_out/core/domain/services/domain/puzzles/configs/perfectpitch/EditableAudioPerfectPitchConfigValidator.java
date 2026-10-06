package org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.configs.perfectpitch;

import java.util.List;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.domain.base.validators.Error;

@Service
public final class EditableAudioPerfectPitchConfigValidator extends EditablePerfectPitchConfigValidator<AudioPerfectPitchConfigAggregate> {

    public List<Error> gatherAllErrors(final AudioPerfectPitchConfigAggregate aggregate) {
        var errors = super.gatherAllErrors(aggregate);
        // TODO EditableAudioPerfectPitchConfigValidator

        return errors;
    }
}
