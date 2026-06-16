// TODO running all test in one click makes all of them failed, whereas running each test package separately passes all of them fine

module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.media;
    requires org.apache.commons.lang3;

    // TODO why to specify these exports?
    exports org.swetlokognatsk.earking_out;
    // TODO remove in prod
    exports org.swetlokognatsk.test;
    exports org.swetlokognatsk.earking_out.app.desktop;
}
