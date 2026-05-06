package org.swetlokognatsk.earking_out.core.ports;

import org.swetlokognatsk.earking_out.core.ports.hints.IHintFinder;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.core.ports.music.INoteNormalizer;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.NoteNormalizer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.HintFinder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch.PerfectPitchHintFinder;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.FakePuzzleGenerator;

// TODO for now this class was made strictly in test purposes, to postpone DI in java
final public class DI {

    private DI() {
    }

    public static <T> T get(Class<T> someClass) {
        // case IPuzzleGenerator.class.getName() -> new TestPuzzleGenerator();
        if (someClass.getName() == INoteNormalizer.class.getName()) {
            return (T)new NoteNormalizer();
        }
        else if (someClass.getName() == IPuzzleGenerator.class.getName()) {
            return (T)new FakePuzzleGenerator();
        }
        else if (someClass.getName() == IHintFinder.class.getName()) {
            return (T)new HintFinder();
        }
        else if (someClass.getName() == IPerfectPitchHintFinder.class.getName()) {
            return (T)new PerfectPitchHintFinder();

        }
        else {
            return null;
        }
    }
}
