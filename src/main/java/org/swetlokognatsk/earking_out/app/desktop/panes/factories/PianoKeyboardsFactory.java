package org.swetlokognatsk.earking_out.app.desktop.panes.factories;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

public final class PianoKeyboardsFactory {

    private PianoKeyboardsFactory() {
    }

    public static PianoKeyboard createPerfectPitchNotesPicker(final double width, final double height, final byte[] selectedKeys) {
        return create(PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER, width, height, selectedKeys);
    }

    public static PianoKeyboard createRootNotePicker(final double width, final double height, final byte[] selectedKeys) {
        return create(PianoKeyboardId.ROOT_NOTE_PICKER, width, height, selectedKeys);
    }

    public static PianoKeyboard createPerfectPitchNotesGuessing(final double width, final double height, final byte[] selectedKeys) {
        return create(PianoKeyboardId.PERFECT_PITCH_NOTES_GUESSING, width, height, selectedKeys);
    }

    private static PianoKeyboard create(final PianoKeyboardId id, final double width, final double height, final byte[] selectedKeys) {
        return new PianoKeyboard(id, width, height, selectedKeys);
    }

}
