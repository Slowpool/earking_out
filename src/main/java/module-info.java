// TODO either make a cooldown after successful guess, either add setting to disable the successful guess note sound.
// TODO make picked notes to be highlighted durin the guessing somehow
// TODO add mode for visual piano key notes picking using mouse
module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.media;
    requires org.apache.commons.lang3;

    exports org.swetlokognatsk.earking_out.app.desktop;
}
