package org.swetlokognatsk.earking_out.app.web.services;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise.unknownExercise;

import org.springframework.stereotype.Component;

// TODO put it in some more specific package
/**
 * Simple puzzle config property - the property, the value of which is always transfered as a string from web client to backend, e.g.: checkbox true/false value, text field's text, select's option. Whereas complicated property must be updated via separate endpoint.
 */
@Component
public class SimplePuzzleConfigPropertiesCaster {

    public Object cast(final Exercise exercise, final String propertyName, final String newValue) {
        var specificCaster = switch (exercise) {
            case AudioPerfectPitchExercise appe -> DI.get(AudioPerfectPitchConfigPropertiesCaster.class);
            default -> throw new IllegalArgumentException(unknownExercise(exercise));
        };
        return specificCaster.cast(propertyName, newValue);
    }
}
