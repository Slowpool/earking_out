package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;

public class InMemoryReadPuzzleConfigService implements ReadPuzzleConfigService {
    public static AudioPerfectPitchConfig puzzleConfig;

    static {
        var notes = new NoteWithAccidental[] { new NoteWithAccidental(NoteNames.D, null, Octaves.FIRST) };
        puzzleConfig = new AudioPerfectPitchConfig(100, false, notes, null, PerfectPitchInputMode.NOTES_AS_CHARACTERS);
    }

    public <E extends Exercise> PuzzleConfig<E> fetch(E exercise) {
        return (PuzzleConfig<E>) puzzleConfig;
    }
}
