module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;

    opens org.swetlokognatsk.earking_out to javafx.fxml;

    exports org.swetlokognatsk.earking_out;
    // TODO remove in prod
    // exports org.swetlokognatsk.test;
    exports org.swetlokognatsk.earking_out.app.desktop;
}
