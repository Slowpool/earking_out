package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

/**
 * This specific enum is used for each PianoKeyboard id because: app has finite number of piano keyboards, each of which is code-dependent - e.g. the same piano keyboard is always used for root note picking in audio perfect pitch exercise. so, it's state is singleton-like. the same thing with other piano keyboards. PianoKeyboard has a state and it's definitely not a value object. according to domain rules, user cannot create new PianoKeyboards, so, their ids are just hardcoded here.
 */
public enum PianoKeyboardId {
    ROOT_NOTE_PICKER, PERFECT_PITCH_NOTES_PICKER, PERFECT_PITCH_NOTES_GUESSING
}
