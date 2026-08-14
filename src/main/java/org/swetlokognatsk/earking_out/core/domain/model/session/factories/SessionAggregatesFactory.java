package org.swetlokognatsk.earking_out.core.domain.model.session.factories;

import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.exceptions.InvalidPuzzleConfigException;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

public final class SessionAggregatesFactory extends AggregatesFactory<SessionAggregate<?, ?, ?, ?>> {
    private final PuzzleConfigRepository puzzleConfigRepository;
    private final PianoKeyboardRepository pianoKeyboardRepository;
    private final PuzzleConfigDTOAssembler puzzleConfigDTOAssembler;
    private final PuzzlesFactory puzzlesFactory;

    public SessionAggregatesFactory(final ObjectCloner cloner, final PuzzleConfigRepository puzzleConfigRepository, final PianoKeyboardRepository pianoKeyboardRepository, final PuzzleConfigDTOAssembler puzzleConfigDTOAssembler, final PuzzlesFactory puzzlesFactory) {
        super(cloner);
        // read-only access
        this.puzzleConfigRepository = puzzleConfigRepository;
        this.pianoKeyboardRepository = pianoKeyboardRepository;

        this.puzzleConfigDTOAssembler = puzzleConfigDTOAssembler;
        this.puzzlesFactory = puzzlesFactory;
    }

    public <E extends Exercise, SA extends SessionAggregate<E, ?, ?, ?>> SA create(final E exercise) {
        var puzzleConfigDto = validateConfigAndGet(exercise);
        // TODO SessionStatsFactory
        var sessionStats = new SessionStats(0, 0);

        var sessionAggregate = switch (exercise) {
        case AudioPerfectPitchExercise _e -> {
            var aggregate = new AudioPerfectPitchSessionAggregate(puzzlesFactory, SessionId.random(), (AudioPerfectPitchConfigDTO) puzzleConfigDto, sessionStats);
            yield aggregate;
        }
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return (SA) sessionAggregate;
    }

    private <E extends Exercise> PuzzleConfigDTO<E> validateConfigAndGet(final E exercise) {
        var puzzleConfigAggregate = puzzleConfigRepository.get(exercise);
        var puzzleConfigDto = puzzleConfigDTOAssembler.assemble(puzzleConfigAggregate);
        if (!puzzleConfigAggregate.isValid()) {
            throw new InvalidPuzzleConfigException(puzzleConfigDto);
        }
        return (PuzzleConfigDTO<E>) puzzleConfigDto;
    }
}
