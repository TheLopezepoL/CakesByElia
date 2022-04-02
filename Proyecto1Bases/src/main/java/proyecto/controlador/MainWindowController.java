package proyecto.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainWindowController {
    //Vista 1 Tablas
    @FXML
    Label label_Tablas;
    @FXML
    Button btn_Tabla1;

    //Vista 2: Vista Principal de Las tablas y menu
    //Menu de opciones de la tabla que se está viendo en ese momento
    @FXML
    Button btn_menu_nuevo;
    @FXML
    Button btn_menu_actualizar;
    @FXML
    Button btn_menu_borrar;
    @FXML
    TextField tf_menu_buscar;
    @FXML
    SplitMenuButton btn_menu_buscar;
    //Tabla
    @FXML
    TableView tableView_tablaActual;

    //Vista3: Menú derecha, Reportes y Info de la sesión y Cerrar sesión
    //Reportes
    @FXML
    Label label_Reportes;
    @FXML
    Button btn_Reporte1;

    //Sesión
    @FXML
    Label label_SesionActual;
    @FXML
    Label label_NombreUsuario;
    @FXML
    Label label_IdEmpleado;
    @FXML
    Label label_PuestoEmpleado;
    @FXML
    Button btn_CerrarSesion;


    public void setInformacionSesion( String pNombreUsuario ){
        label_NombreUsuario.setText("User: " + pNombreUsuario);
    }




}
