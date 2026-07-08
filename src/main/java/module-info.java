// TODO how to run junit via console
module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.media;
    requires org.apache.commons.lang3;

    // TODO running all test in one click makes all of them failed, whereas running each test package separately passes all of them fine. further lines make several tests work fine, as if this new module uses these packages. then why running each test separately does not require them?
    // TODO do javafx app uses it? try to run app without this export
    exports org.swetlokognatsk.earking_out.app.desktop;
}
