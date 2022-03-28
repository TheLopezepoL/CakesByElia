module com.bases2.proyecto1bases {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.bases2.proyecto1bases to javafx.fxml;
    exports com.bases2.proyecto1bases;
    exports com.bases2.proyecto1bases.controlador;
    opens com.bases2.proyecto1bases.controlador to javafx.fxml;
}