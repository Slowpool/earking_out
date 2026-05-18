package org.swetlokognatsk.earking_out.app.desktop.helpers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public final class RadioButtonHelper {
    public static <E extends Enum<?>> RadioButton[] makeList(final Class<E> enumClass, final ToggleGroup toggleGroup, final EventHandler<ActionEvent> handler) {
        final var enumElements = enumClass.getEnumConstants();
        final var radioButtons = new RadioButton[enumElements.length];

        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i] = createRadioButton(enumElements[i], toggleGroup, handler);
        }

        return radioButtons;
    }

    public static <E extends Enum<?>> RadioButton[] makeList(final Class<E> enumClass, final ToggleGroup toggleGroup) {
        return makeList(enumClass, toggleGroup, null);
    }

    private static <E extends Enum<?>> RadioButton createRadioButton(E enumElement, final ToggleGroup toggleGroup, final EventHandler<ActionEvent> handler) {
        String name = enumElement.name();
        String humanizedName = humanize(name);

        var radioButton = new RadioButton(humanizedName);
        radioButton.setId(name);
        radioButton.setToggleGroup(toggleGroup);
        if (handler != null) {
            radioButton.setOnAction(handler);
        }

        return radioButton;
    }

    private static String humanize(String name) {
        String humanizedName = name.replace('_', ' ');
        humanizedName = humanizedName.toLowerCase();
        return humanizedName;
    }
}
