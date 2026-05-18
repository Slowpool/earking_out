package org.swetlokognatsk.earking_out.app.desktop.components;

public enum PianoKeyboardMode {
    ONE_KEY_TOUCH, // when you press the key, it is highlighted during the key holding. then, after releasing the key, highlighting disappears
    ONE_KEY_SELECT, // the pressed key becomes highlighted until another key is pressed
    SEVERAL_KEYS_SELECT, // the pressed key becomes highlighted until it's pressed again. any number of keys can be highlighted at the same time
    SEVERAL_KEYS_SELECT_3_OCTAVES // taking the standard office keyboard constraint of having just 22 colums on it `zxcvbnm,./qwertyuiop[]` as business logic invariant
}
