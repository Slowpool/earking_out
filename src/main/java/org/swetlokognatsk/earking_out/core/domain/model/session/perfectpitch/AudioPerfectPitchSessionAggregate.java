package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import java.util.UUID;

import org.swetlokognatsk.earking_out.app.desktop.services.AudioHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class AudioPerfectPitchSessionAggregate extends PerfectPitchSessionAggregate<AudioPerfectPitchExercise, AudioPerfectPitchPuzzle, AudioPerfectPitchConfigDTO> {
    protected final AudioHintPlayer<UsualHint> audioHintPlayer;

    public AudioPerfectPitchSessionAggregate(final UUID id, final AudioPerfectPitchConfigDTO puzzleConfigDto, final SessionStats stats) {
        super(id, puzzleConfigDto, stats);
                var audioHintPlayer = DI.get(AudioHintPlayer.class);
    }

    // TODO refactoring
    protected void demonstrateNewHint() {
        audioHintPlayer.prepareHint(puzzle.hint);
        demonstrateHint();
    }

    protected void demonstrateHint() {
        audioHintPlayer.stopAndPlay();
    }
}
