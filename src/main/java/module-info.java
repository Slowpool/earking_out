// TODO cooldown after successful guess? to avoid audio abuse of intervals in perfect pitch
// TODO make picked notes to be highlighted durin the guessing somehow
// TODO add mode for visual piano key notes picking using mouse
module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.media;
    requires org.apache.commons.lang3;
    // TODO how to eliminate this endless requires
    requires spring.boot;
    requires spring.context;
    requires spring.beans;

    exports org.swetlokognatsk.earking_out.app.desktop;
}
