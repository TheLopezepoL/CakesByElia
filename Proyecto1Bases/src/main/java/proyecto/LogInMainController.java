package proyecto;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyecto.modelo.Empleado;
import proyecto.modelo.OracleJBDC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

public class LogInMainController {
    //Componentes de la vista
    @FXML
    private Label label_derecha_login;
    @FXML
    private Label label_izquierda_login;

    @FXML
    private TextField tf_usuario_login;
    @FXML
    private TextField tf_contrasenia_login;

    @FXML
    private Button ingresar_btn_login;

    //Para Cambiar la Escena
    private Stage escenario;
    private Scene escena;
    private Parent root;

    private OracleJBDC JBDC_Instacia = OracleJBDC.getInstancia();



    @FXML
    protected void iniciarSesion(ActionEvent evento) throws IOException, SQLException {
        String usuario = tf_usuario_login.getText();
        String contrasenia = tf_contrasenia_login.getText();

        System.out.println("User: " + usuario + " Password:" + contrasenia);


        //Probar Conexión con la base de datos
        //probarBase();  //Debe retornar un mensaje para saber si hay conexion
        int idEmpleado = JBDC_Instacia.validar_credenciales(usuario, contrasenia);

        if( idEmpleado != 0 ){
            Empleado empleado = JBDC_Instacia.getEmpleado(idEmpleado);
            cambiarEscena(empleado, evento);
        }else {
            System.out.println("Usuario o contraseña incorrectos");
        }

    }

    //Cambia a la escena principal
    public void cambiarEscena( Empleado pEmpleado, ActionEvent evento ) throws IOException, SQLException {

        System.out.println(pEmpleado);


        FXMLLoader loader = new FXMLLoader( getClass().getResource("vista/mainwindow.fxml") );  //Carga la Escena
        root = loader.load();

        MainWindowController mainWindowController = loader.getController();      //Nos da el controlador de la vista para acceder a los metodos

        mainWindowController.setInformacionSesion( pEmpleado );               //Ejecuta el mentodo de la escena de la ventana principal

        //Cambia la escena con el evento del botón de iniciar sesion
        escenario = (Stage)( (Node)evento.getSource() ).getScene().getWindow();  //Busca el escenario donde colocar la escena
        escena = new Scene(root);                                                //Crea la escena
        escenario.setScene(escena);                                              //Coloca la escena en el escenario
        escenario.show();                                                        //Muestra la escena

    }


    protected void probarBase() {
        //OracleJBDC test = new OracleJBDC();
        JBDC_Instacia.probarConexion();
    }


}