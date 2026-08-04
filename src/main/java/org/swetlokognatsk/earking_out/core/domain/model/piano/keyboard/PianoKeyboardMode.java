package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.apache.commons.lang3.ArrayUtils;

public enum PianoKeyboardMode {
    ONE_KEY_TOUCH, // when you press the key, it is highlighted during the key holding. then, after releasing the key, highlighting disappears
    ONE_KEY_SELECT, // the pressed key becomes highlighted until another key is pressed
    SEVERAL_KEYS_SELECT, // the pressed key becomes highlighted until it's pressed again. any number of keys can be highlighted at the same time
    SEVERAL_KEYS_SELECT_3_OCTAVES; // taking the standard office keyboard constraint of having just 22 colums on it `zxcvbnm,./qwertyuiop[]` as business logic invariant

    private static final PianoKeyboardMode[] touchModes = new PianoKeyboardMode[] { ONE_KEY_TOUCH };
    private static final PianoKeyboardMode[] selectModes = new PianoKeyboardMode[] { ONE_KEY_SELECT, SEVERAL_KEYS_SELECT, SEVERAL_KEYS_SELECT_3_OCTAVES };

    public boolean isSelectMode() {
        return ArrayUtils.contains(selectModes, this);
    }

    public boolean isTouchMode() {
        return ArrayUtils.contains(touchModes, this);
    }
}
