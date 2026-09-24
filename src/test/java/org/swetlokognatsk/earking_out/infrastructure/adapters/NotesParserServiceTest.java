package org.swetlokognatsk.earking_out.infrastructure.adapters;

import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesNormalizingService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.music.NotesParsingService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public class NotesParserServiceTest {

    private static final NotesParsingService noteParser = DI.get(NotesParsingService.class);

    @Test
    public void parseCorrectNotes() {
        // TODO here i go
        var correctNotes = buildCorrectNotes();

        foreach(var )
    }
}
