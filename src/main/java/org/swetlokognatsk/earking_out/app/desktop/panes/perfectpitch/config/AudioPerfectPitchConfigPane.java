package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;

public class AudioPerfectPitchConfigPane extends PerfectPitchConfigPane<AudioPerfectPitchExercise, AudioPerfectPitchConfig> {

    public AudioPerfectPitchConfigPane(final AudioPerfectPitchConfig puzzleConfig) {
        super(puzzleConfig);

    }

    protected void setFieldsValuesFromConfig(AudioPerfectPitchConfig puzzleConfig) {
        super.setFieldsValuesFromConfig((PerfectPitchConfig<AudioPerfectPitchExercise>)puzzleConfig);
    }

    // TODO it's not needed anymore, but remained for backward mapping (from pane to config store)
    // protected AudioPerfectPitchConfig mapToDomainConfig() {
    //     //     // TODO ParsingException?
    //     //     var targetNumberOfPuzzles = Integer.valueOf(numberOfPuzzlesField.getText());
    //     //     var statsRecording = statisticsRecordingField.isSelected();
    //     //     var notes = new NoteWithAccidental[] {};
    //     //     var inputMode = 

    //     //     var config = new AudioPerfectPitchConfig(targetNumberOfPuzzles, statsRecording, notes, null, inputMode);
    //     // return config;
    //     return null;
    // }
}
