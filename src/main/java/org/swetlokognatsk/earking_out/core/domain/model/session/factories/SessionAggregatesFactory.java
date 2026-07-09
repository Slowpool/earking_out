package org.swetlokognatsk.earking_out.core.domain.model.session.factories;

import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.base.Factory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.exceptions.InvalidPuzzleConfigException;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardStorageAdapter;

public final class SessionAggregatesFactory extends Factory<SessionAggregate<?, ?, ?, ?>, DependentAggregatesDTO> {
    protected final PuzzleConfigRepository puzzleConfigRepository;
    protected final PianoKeyboardStorageAdapter pianoKeyboardRepository;

    public SessionAggregatesFactory() {
        // read-only access
        puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);
        pianoKeyboardRepository = DI.get(PianoKeyboardStorageAdapter.class);
    }

    // TODO do something with this cringe
    public SessionAggregate<?, ?, ?, ?> createDefault(final DependentAggregatesDTO dependentAggregates) {
        throw new RuntimeException("there are no default sessions. it must have some exercise");
    }

    public <E extends Exercise, SA extends SessionAggregate<E, ?, ?, ?>> SA create(final E exercise) {
        var puzzleConfigDto = validateConfigAndGet(exercise);
        // TODO SessionStatsFactory
        var sessionStats = new SessionStats(0, 0);

        var sessionAggregate = switch (exercise) {
        case AudioPerfectPitchExercise _e ->  {
            var notesGuessingPianoKeyboard = pianoKeyboardRepository.get(PianoKeyboardId.PERFECT_PITCH_NOTES_GUESSING);
            var aggregate = new AudioPerfectPitchSessionAggregate(SessionId.random(), (AudioPerfectPitchConfigDTO) puzzleConfigDto, sessionStats, notesGuessingPianoKeyboard);
            yield aggregate;
        }
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return (SA) sessionAggregate;
    }

    protected <E extends Exercise> PuzzleConfigDTO<E> validateConfigAndGet(final E exercise) {
        var puzzleConfigAggregate = puzzleConfigRepository.get(exercise);
        var puzzleConfigDto = PuzzleConfigDTOAssembler.assemble(puzzleConfigAggregate);
        if (!puzzleConfigAggregate.isValid()) {
            throw new InvalidPuzzleConfigException(puzzleConfigDto);
        }
        return (PuzzleConfigDTO<E>) puzzleConfigDto;
    }
}
