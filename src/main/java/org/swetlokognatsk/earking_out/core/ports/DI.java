package org.swetlokognatsk.earking_out.core.ports;

import org.swetlokognatsk.earking_out.core.ports.hints.IHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.core.ports.music.INoteNormalizer;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.NoteNormalizer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.HintFinderByExercise;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeAudioPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.PerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.FakeVisualPerfectPitchHints;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.FakePuzzleGenerator;

// TODO for now this class was made strictly in test purposes, to postpone DI in java
final public class DI {

    private DI() {
    }

    public static <T> T get(Class<T> someClass) {
        var className = someClass.getName();
        // case IPuzzleGenerator.class.getName() -> new TestPuzzleGenerator();
        if (className == INoteNormalizer.class.getName()) {
            return (T)new NoteNormalizer();
        }
        else if (className == IPuzzleGenerator.class.getName()) {
            return (T)new FakePuzzleGenerator();
        }
        else if (className == IHintFinder.class.getName()) {
            return (T)new HintFinderByExercise();
        }
        else if (className == IPerfectPitchHintFinder.class.getName()) {
            return (T)new PerfectPitchHintFinder();
        }
        else if (className == FakeVisualPerfectPitchHints.class.getName()) {
            return (T)new FakeVisualPerfectPitchHints();
        }
        else if (className == FakeAudioPerfectPitchHints.class.getName()) {
            return (T)new FakeAudioPerfectPitchHints();
        }
        else {
            return null;
        }
    }
}
