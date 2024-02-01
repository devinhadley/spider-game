module com.example.spiderworld2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.spiderworld2 to javafx.fxml;
    exports com.example.spiderworld2;
}