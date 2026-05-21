package org.swetlokognatsk.earking_out.app.desktop.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.intervals.melodic.VisualMelodicIntervalsExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.VisualPerfectPitchExercise;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public final class ExercisesMenu extends Menu {
    private static final List<Exercise> EXERCISES_LIST = List.of(new AudioPerfectPitchExercise(), new VisualPerfectPitchExercise(), new VisualMelodicIntervalsExercise());

    private final Map<Exercise, MenuItem> menuItems = new HashMap<>();

    private HashMap<ExerciseNames, Menu> menus = new HashMap<>();

    public ExercisesMenu(String text, EventHandler<ActionEvent> onAction) {
        super(text);

        MenuItem menuItem;
        for (var exercise : EXERCISES_LIST) {
            menuItem = ensureExerciseInMenu(exercise);
            menuItem.setOnAction(onAction);
        }
        addMenus();
    }

    private MenuItem ensureExerciseInMenu(Exercise exercise) {
        var menu = ensureAndGetMenu(exercise);

        var menuItem = new MenuItem(exercise.tType());
        menuItem.setUserData(exercise);

        var menuItems = menu.getItems();
        menuItems.add(menuItem);

        this.menuItems.put(exercise, menuItem);

        return menuItem;
    }

    private Menu ensureAndGetMenu(Exercise exercise) {
        if (menus.get(exercise.name) == null) {
            var newMenu = new Menu(exercise.tName());
            menus.put(exercise.name, newMenu);
        }
        return menus.get(exercise.name);
    }

    private void addMenus() {
        var builtMenus = menus.values();
        getItems().addAll(builtMenus);
        menus = null;
    }

    public void fireExercise(Exercise exercise) {
        if (!menuItems.containsKey(exercise)) {
            throw new IllegalArgumentException("exercises menu does not have such an exercise");
        }

        var menuItem = menuItems.get(exercise);
        menuItem.fire();
    }
}
