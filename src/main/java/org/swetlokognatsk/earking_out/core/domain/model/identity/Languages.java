package org.swetlokognatsk.earking_out.core.domain.model.identity;

public enum Languages {
    en("en"), ru("ru");

    public static final Languages DEFAULT = en;
    public final String value;

    private Languages(final String value) {
        this.value = value;
    }
}
