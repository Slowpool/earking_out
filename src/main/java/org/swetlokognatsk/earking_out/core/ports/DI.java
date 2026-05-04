package org.swetlokognatsk.earking_out.core.ports;

import org.swetlokognatsk.earking_out.core.ports.music.INoteNormalizer;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.NoteNormalizer;

// TODO for now this class was made strictly in test purposes, to postpone DI in java
final public class DI {

    private DI() {
    }

    public static <T> T get(Class<T> someClass) {
        // case IPuzzleGenerator.class.getName() -> new TestPuzzleGenerator();
        if (someClass.getName() == INoteNormalizer.class.getName()) {
            return (T)new NoteNormalizer();
        }
        else {
            return null;
        }
    }
}
