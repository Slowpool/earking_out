package org.swetlokognatsk.earking_out.core.domain.model;

public class UsualHint extends Hint {
    final String hint;

    public UsualHint(String hint) {
        this.hint = hint;
    }

    public String getValue() {
        return hint;
    }
}
