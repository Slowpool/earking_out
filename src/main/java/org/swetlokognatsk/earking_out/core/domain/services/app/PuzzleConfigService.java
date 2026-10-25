package org.swetlokognatsk.earking_out.core.domain.services.app;

import java.util.Objects;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateNotFoundException;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard.PianoKeyboardDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PersistentPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

@Service
public final class PuzzleConfigService {

    private final PersistentPuzzleConfigRepository repository;
    private final PianoKeyboardRepository pianoKeyboardRepository;

    public PuzzleConfigService(final PersistentPuzzleConfigRepository repository, final PianoKeyboardRepository pianoKeyboardRepository) {
        this.repository = Objects.requireNonNull(repository);
        this.pianoKeyboardRepository = pianoKeyboardRepository;
    }

    public void updateProperty(final Exercise exercise, final String property, final Object value) {
        var puzzleConfigAggregate = repository.getPuzzleConfig(exercise);
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
        try {
            var pianoKeyboardDto = pianoKeyboardRepository.getPianoKeyboardDTO(pianoKeyboardId);
            var propertyValue = mapPianoKeyboardToPropetyValue(pianoKeyboardDto, propertyName);
            puzzleConfigAggregate.updateProperty(propertyName, propertyValue);
            repository.save(puzzleConfigAggregate);
        } catch (AggregateNotFoundException e) {
            // TODO send feedback somehow
            throw new RuntimeException("piano keyboard not found: " + pianoKeyboardId);
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
    protected Object mapPianoKeyboardToPropetyValue(final PianoKeyboardDTO pianoKeyboardDto, final String propertyName) {
        var propertyValue = switch (propertyName) {
        case PerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP -> pianoKeyboardDto.selectedKeys()[0];
        case PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP -> pianoKeyboardDto.selectedKeys();
        default -> throw new RuntimeException("unknown property: " + propertyName);
        };
        return propertyValue;
    }

    private <E extends Exercise> PuzzleConfigAggregate<E> getPuzzleConfigAggregate(final E exercise) {
        var puzzleConfigAggregate = repository.getPuzzleConfig(exercise);
        return puzzleConfigAggregate;
    }
}
