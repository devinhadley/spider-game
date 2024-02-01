module com.example.spiderworld2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.spiderworld2 to javafx.fxml;
    exports com.example.spiderworld2;
    exports org.studs.spidergame;
    opens org.studs.spidergame to javafx.fxml;
}