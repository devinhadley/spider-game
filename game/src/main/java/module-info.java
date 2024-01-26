module org.studs.spidergame {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens org.studs.spidergame to javafx.fxml;
    exports org.studs.spidergame;
}