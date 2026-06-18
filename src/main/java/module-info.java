module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.media;
    requires org.apache.commons.lang3;

    // TODO running all test in one click makes all of them failed, whereas running each test package separately passes all of them fine. further lines make several tests work fine, as if this new module uses these packages. then why running each test separately does not require them?
    exports org.swetlokognatsk.earking_out.app.desktop;
    // exports org.swetlokognatsk.test;
    // exports org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;
    // exports org.swetlokognatsk.earking_out.core.domain.model.piano.key;
    // exports org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;
}
