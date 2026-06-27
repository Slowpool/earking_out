package org.swetlokognatsk.earking_out.core.domain.model.hints;

public abstract class TextHint extends Hint {
    final String hint;

    public TextHint(final String hint) {
        this.hint = hint;
    }

    public String getValue() {
        return hint;
    }
}
