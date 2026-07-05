package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.panes.PuzzlePane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;

public abstract class PerfectPitchPane<E extends PerfectPitchExercise, PCDTO extends PerfectPitchConfigDTO<E>, SS extends SessionService<E, ? extends SessionAggregate<E, ?, ?, PCDTO>, ? extends SessionRepository<?>>> extends PuzzlePane<E, PCDTO, SS> {

    public PerfectPitchPane(final UUID sessionId, final PCDTO config, final double width, final double height, final SS sessionService) {
        super(sessionId, config, width, height, sessionService);
    }
}
