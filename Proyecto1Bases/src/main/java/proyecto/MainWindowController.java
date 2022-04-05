package proyecto;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import proyecto.modelo.Empleado;
import proyecto.modelo.MainWindowModel;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainWindowController {

    MainWindowModel mainWindowModel = null;

    //Vista 1 Tablas
    @FXML
    private VBox vbox_botones_tablas;
    @FXML
    private Label label_Tablas;
    @FXML
    private Button btn_Tabla1;

    //Vista 2: Vista Principal de Las tablas y menu
    //Menu de opciones de la tabla que se está viendo en ese momento
    @FXML
    private Button btn_menu_nuevo;
    @FXML
    private Button btn_menu_actualizar;
    @FXML
    private Button btn_menu_borrar;
    @FXML
    private TextField tf_menu_buscar;
    @FXML
    private SplitMenuButton btn_menu_buscar;
    //Tabla
    @FXML
    private TableView tableView_tablaActual;

    //Vista3: Menú derecha, Reportes y Info de la sesión y Cerrar sesión
    //Reportes
    @FXML
    private Label label_Reportes;
    @FXML
    private Button btn_Reporte1;

    //Sesión
    @FXML
    private Label label_SesionActual;
    @FXML
    private Label label_NombreUsuario;
    @FXML
    private Label label_IdEmpleado;
    @FXML
    private Label label_PuestoEmpleado;
    @FXML
    private Button btn_CerrarSesion;

    //Constructor de la vara
    public void setInformacionSesion( Empleado pEmpleado ) throws SQLException {
        mainWindowModel = new MainWindowModel( pEmpleado );
        label_NombreUsuario.setText(mainWindowModel.getEmpleado().getNombre() + mainWindowModel.getEmpleado().getApellido1());
        label_IdEmpleado.setText( "" + mainWindowModel.getEmpleado().getId());
        label_PuestoEmpleado.setText("Id: " + mainWindowModel.getEmpleado().getPuesto());

        setBotonesTablas(this.mainWindowModel.getListaTablas());
    }

    //Crea botones de las tablas y los agrega a la vista de los botones.
    private void setBotonesTablas(ArrayList<String> pListaTabaslUser){
        //vbox_botones_tablas.getChildren().removeAll();// //Elimina todos luego los pone
        for (String nombreTabla: pListaTabaslUser) {
            Button tempButton = new Button(nombreTabla);
            tempButton.setId("btn_" + nombreTabla);
            tempButton.setOnAction(this::handlerBotonesMain);
            vbox_botones_tablas.getChildren().add(tempButton);

        }

    }

    //Todos los botones, excepto el botón de buscar, que no sé como manejar ese todavía, fijo es parecido, pero who knows
    public void handlerBotonesMain(Event e ){

        String id = ((Node) e.getSource()).getId();

        System.out.println("Boton presionado: " + id);

        switch(id) {
            case "btn_EMPLEADO":
                System.out.println("Cargando Vista de Empleado");
                break;
            case "btn_SUCURSAL":
                System.out.println("Cargando Vista de Sucursal");
                break;
            case "btn_USUARIO":
                System.out.println("Cargando Vista de Usuario");
                break;
            case "btn_menu_buscar":
                System.out.println("Iniciando Busqueda");
                busquedaTabla( btn_menu_buscar.getText() );
                break;
        }
    }


    //Cuando se Presiona el boton de buscar acá se hace la lógica de la búsqueda
    private void busquedaTabla(String pBusqueda){
        System.out.println("Buscando por: " + pBusqueda);
    }

    //Cambia la busqueda con el menu button
    public void cambiarBusqueda(Event e){
        String textMenu = ((MenuItem) e.getSource() ).getText();
        btn_menu_buscar.setText(textMenu);
    }


}
