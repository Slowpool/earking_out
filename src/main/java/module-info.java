// TODO cooldown after successful guess? to avoid audio abuse of intervals in perfect pitch
// TODO make picked notes to be highlighted durin the guessing somehow
// TODO add mode for visual piano key notes picking using mouse
module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.media;
    requires org.apache.commons.lang3;
    // TODO how to eliminate this endless requires
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;

    // TODO sort it out
    exports org.swetlokognatsk.earking_out.app.desktop;
    exports org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring to spring.beans, spring.context;
    exports org.swetlokognatsk.earking_out.core.ports.piano to spring.context;
    
    opens org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring to spring.core;
    opens org.swetlokognatsk.earking_out.core.ports.piano;
}
