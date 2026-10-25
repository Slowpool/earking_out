package org.swetlokognatsk.earking_out.core.domain.model.session.factories;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.GeneralSessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesParsingService;
import org.swetlokognatsk.earking_out.core.ports.base.ObjectCloner;
import org.swetlokognatsk.earking_out.core.ports.config.PersistentPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

@Service
public final class SessionAggregatesFactory extends AggregatesFactory<SessionAggregate<?, ?, ?, ?>> {
    private final PersistentPuzzleConfigRepository puzzleConfigRepository;
    private final PuzzleConfigDTOAssembler puzzleConfigDTOAssembler;
    private final PuzzlesFactory puzzlesFactory;

    public SessionAggregatesFactory(final ObjectCloner cloner, final PersistentPuzzleConfigRepository puzzleConfigRepository, final PuzzleConfigDTOAssembler puzzleConfigDTOAssembler, final PuzzlesFactory puzzlesFactory) {
        super(cloner);
        // read-only access
        this.puzzleConfigRepository = puzzleConfigRepository;

        this.puzzleConfigDTOAssembler = puzzleConfigDTOAssembler;
        this.puzzlesFactory = puzzlesFactory;
    }

    public <E extends Exercise, SA extends SessionAggregate<E, ?, ?, ?>> SA create(final E exercise) {
        var puzzleConfigDto = getPuzzleConfigDto(exercise);
        // TODO SessionStatsFactory
        var sessionStats = new GeneralSessionStats(0, 0);

        var sessionAggregate = switch (exercise) {
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchSessionAggregate(puzzlesFactory, SessionId.random(), (AudioPerfectPitchConfigDTO) puzzleConfigDto, sessionStats, DI.get(NotesParsingService.class), DI.get(NotesNormalizingService.class));
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return (SA) sessionAggregate;
    }

    private <E extends Exercise> PuzzleConfigDTO<E> getPuzzleConfigDto(final E exercise) {
        var puzzleConfigDto = puzzleConfigRepository.getPuzzleConfigDTO(exercise);
        return (PuzzleConfigDTO<E>) puzzleConfigDto;
    }
}
