package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle.AudioPerfectPitchPane;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.perfectpitch.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.annotations.DesktopService;
import lombok.AllArgsConstructor;

@DesktopService
@AllArgsConstructor
public final class PuzzlePanesFactory {

    private final SessionRepositoryDelegator sessionRepository;

    public PuzzlePane<?, ?, ?> create(final SessionId sessionId, final double width, final double height) {
        var session = sessionRepository.get(sessionId);
        var puzzleConfigDto = session.getPuzzleConfig();
        var exercise = puzzleConfigDto.exercise;
        var service = switch (exercise) {
        case AudioPerfectPitchExercise e -> DI.get(AudioPerfectPitchSessionService.class);
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };

        var puzzlePane = switch (exercise) {
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchPane(sessionId, (AudioPerfectPitchConfigDTO) puzzleConfigDto, width, height, (AudioPerfectPitchSessionService) service, DI.get(PianoKeyboardHandlersRegister.class), DI.get(PianoKeyboardService.class), DI.get(SessionRepositoryDelegator.class));
        default -> throw new RuntimeException("unkown exercise: " + exercise);
        };

        return puzzlePane;
    }
}
