package org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch;

import java.io.Serializable;

import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.PianoKeyNumberSolution;

public final class VisualPerfectPitchSolution extends PerfectPitchSolution implements Serializable {

    public VisualPerfectPitchSolution(final Note note) {
        super(note);
    }
}