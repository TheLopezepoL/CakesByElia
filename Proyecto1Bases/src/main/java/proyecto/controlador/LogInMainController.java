package proyecto.controlador;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import proyecto.modelo.OracleJBDC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LogInMainController {
    @FXML
    private Label label_derecha_login;
    private Label label_izquierda_login;
    private TextField tf_usuario_login;
    private TextField tf_contrasenia_login;
    private Button ingresar_btn_login;


    @FXML
    protected void iniciarSesion(){
//        String usuario = tf_usuario_login.getText();
//        String contrasenia = tf_contrasenia_login.getText();

//        System.out.println("User: " + usuario + " Password:" + contrasenia);
        System.out.println("Hola");
    }


    @FXML
    protected void onHelloButtonClick() {
        OracleJBDC test = new OracleJBDC();
        test.probarConexion("jdbc:oracle:thin:@192.168.100.90:1521:ORCLCDB","sys as sysdba", "oracle");
    }
}