package org.swetlokognatsk.earking_out.core.domain.services.app;

import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDtoAssembler;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

public final class PuzzleConfigService {

    private final PuzzleConfigRepository repository;
    private final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler;

    public PuzzleConfigService(final PuzzleConfigRepository repository, final PianoKeyboardDtoAssembler pianoKeyboardDtoAssembler) {
        this.repository = Objects.requireNonNull(repository);
        this.pianoKeyboardDtoAssembler = pianoKeyboardDtoAssembler;
    }

    public void updateProperty(final Exercise exercise, final String property, final Object value) {
        var puzzleConfigAggregate = repository.get(exercise);
        try {
            puzzleConfigAggregate.updateProperty(property, value);
            repository.save(puzzleConfigAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
        }
    }

    // TODO create separated PerfectPitchConfigService?
    public void updatePropertyViaPianoKeyPressing(final PianoKeyboardId pianoKeyboardId, final PianoKeyNumber keyNumber) {
        var puzzleConfigAggregate = getPuzzleConfigAggregate(pianoKeyboardId.exercise);

        var propertyName = getPianoKeyboardPropertyName(pianoKeyboardId);
        var pianoKeyboardDto = pianoKeyboardDtoAssembler.getPianoKeyboardDTO(pianoKeyboardId);
        var propertyValue = getPianoKeyboardPropetyValue(propertyName, pianoKeyboardDto);
        try {
            puzzleConfigAggregate.updateProperty(propertyName, propertyValue);
            repository.save(puzzleConfigAggregate);
        }
        // TODO just Exception?
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected String getPianoKeyboardPropertyName(final PianoKeyboardId pianoKeyboardId) {
        return switch (pianoKeyboardId) {
        case AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER -> PerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP;
        case AUDIO_PERFECT_PITCH_NOTES_PICKER -> PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP;
        default -> throw new IllegalArgumentException("this piano keyboard is not for config: " + pianoKeyboardId);
        };
    }

    // TODO add validation for root note on-change (not on exercise starting, though add the whole config validation should be added before exercise starting)
    protected Object getPianoKeyboardPropetyValue(final String propertyName, final PianoKeyboardDTO pianoKeyboardDto) {
        var propertyValue = switch (propertyName) {
        case PerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP -> pianoKeyboardDto.selectedKeys()[0];
        case PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP -> pianoKeyboardDto.selectedKeys();
        default -> throw new RuntimeException("unknown property: " + propertyName);
        };
        return propertyValue;
    }

    private <E extends Exercise> PuzzleConfigAggregate<E> getPuzzleConfigAggregate(final E exercise) {
        var puzzleConfigAggregate = repository.get(exercise);
        return (PuzzleConfigAggregate<E>) puzzleConfigAggregate;
    }
}
