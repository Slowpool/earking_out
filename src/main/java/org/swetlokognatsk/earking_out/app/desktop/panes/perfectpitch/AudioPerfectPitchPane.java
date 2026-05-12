package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;

public class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchExercise, AudioPerfectPitchConfig> {
    
    public AudioPerfectPitchPane(AudioPerfectPitchConfig config) {
        super(config);
        
    }
}

// TODO check that all project package names do not contain _ (those that were added by me)