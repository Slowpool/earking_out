// TODO cooldown after successful guess? to avoid audio abuse of intervals in perfect pitch
// TODO make picked notes to be highlighted durin the guessing somehow
// TODO add mode for visual piano key notes picking using mouse
module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.media;
    requires org.apache.commons.lang3;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    // further exports/opens are definitely cluttering. spring recommends to delete the module-info.java file at all because spring requires reflection over almost the whole code base. nevertheless i decided to keep them in learning/training purposes. i wanna learn and detect the debugging and type-is-not-exported/opened-errors
    exports org.swetlokognatsk.earking_out.app.desktop;
    exports org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;
    exports org.swetlokognatsk.earking_out.core.ports.piano;
    exports org.swetlokognatsk.earking_out.core.ports.hints.demonstrators;
    exports org.swetlokognatsk.earking_out.core.domain.events;
    exports org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard;
    exports org.swetlokognatsk.earking_out.core.domain.events.puzzles;
    exports org.swetlokognatsk.earking_out.core.domain.model.solutions;

    opens org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;
}
