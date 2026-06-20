package org.swetlokognatsk.earking_out.core.domain.services.app.dto.piano.keyboard;

import org.junit.Before;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;

public final class PianoKeyboardDTOAssemblerTest {
    protected final PianoKeyboardAggregatesFactory pianoKeyboardFactory;

    protected PianoKeyboardDtoAssembler assembler;

    public PianoKeyboardDTOAssemblerTest() {
        pianoKeyboardFactory = new PianoKeyboardAggregatesFactory();
    }

    @Before
    public void setup() {
        this.assembler = new PianoKeyboardDtoAssembler();
    }

    @Test
    public void assembling1() {
        var pianoKeyboardAggregate = pianoKeyboardFactory.createDefault();
        assembler.assemble();
    }

    @Test
    public void assembling2() {

    }

    @Test
    public void assembling3() {

    }
}
