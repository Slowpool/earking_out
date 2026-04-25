module org.swetlokognatsk {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.swetlokognatsk.earking_out to javafx.fxml;
    exports org.swetlokognatsk.earking_out;
}
