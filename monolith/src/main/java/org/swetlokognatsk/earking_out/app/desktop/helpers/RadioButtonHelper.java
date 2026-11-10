package org.swetlokognatsk.earking_out.app.desktop.helpers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public final class RadioButtonHelper {

    private RadioButtonHelper() {
    }

    public static <E extends Enum<?>> RadioButton[] makeList(final Class<E> enumClass, final ToggleGroup toggleGroup, E selectedValue, final EventHandler<ActionEvent> handler) {
        final var enumElements = enumClass.getEnumConstants();
        final var radioButtons = new RadioButton[enumElements.length];

        RadioButton radioButton;
        E enumElement;
        for (int i = 0; i < radioButtons.length; i++) {
            enumElement = enumElements[i];

            radioButton = createRadioButton(enumElement, toggleGroup, handler);
            radioButtons[i] = radioButton;

            if (enumElement == selectedValue) {
                toggleGroup.selectToggle(radioButton);
            }
        }

        return radioButtons;
    }

    public static <E extends Enum<?>> RadioButton[] makeList(final Class<E> enumClass, final ToggleGroup toggleGroup, E selectedValue) {
        return makeList(enumClass, toggleGroup, selectedValue, null);
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
