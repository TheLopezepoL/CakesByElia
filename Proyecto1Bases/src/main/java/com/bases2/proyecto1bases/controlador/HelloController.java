package com.bases2.proyecto1bases.controlador;

import com.bases2.proyecto1bases.modelo.OracleJBDC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {


        OracleJBDC test = new OracleJBDC();
        test.probarConexion("jdbc:oracle:thin:@192.168.100.90:1521:ORCLCDB","sys as sysdba", "oracle");
    }
}