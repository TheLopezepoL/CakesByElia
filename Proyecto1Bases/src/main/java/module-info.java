module proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens proyecto to javafx.fxml;
    exports proyecto;


}