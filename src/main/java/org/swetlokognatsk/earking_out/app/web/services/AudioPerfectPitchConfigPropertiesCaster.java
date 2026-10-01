package org.swetlokognatsk.earking_out.app.web.services;

import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;

import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate.STATS_RECORDING_PROP;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate.*;

@Component
public class AudioPerfectPitchConfigPropertiesCaster {

    public Object cast(final String propertyName, final String newValue) {
        return switch (propertyName) {
        case TARGET_NUMBER_OF_PUZZLES_PROP -> Integer.valueOf(newValue);
        case STATS_RECORDING_PROP, SOUNDLESS_GUESSING_PIANO_PROP -> newValue != null && newValue.toLowerCase().equals("on")
                ? true
                : false;
        case INPUT_MODE_PROP -> PerfectPitchInputMode.valueOf(newValue);
        default -> throw new IllegalArgumentException("unknown puzzle config property: %s".formatted(propertyName));
        };
    }

}
