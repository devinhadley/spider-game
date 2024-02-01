module com.example.spiderworld {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.studs.spidergame to javafx.fxml;
    exports org.studs.spidergame;
}