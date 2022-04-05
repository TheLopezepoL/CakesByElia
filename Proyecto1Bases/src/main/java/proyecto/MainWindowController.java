package proyecto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
    //Anchor Pane Para Tbala
    @FXML
    private AnchorPane anchorPane_paraTabla;

    @FXML
    private BorderPane borderPane_centerTable;

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



    //Constructor
    public void setInformacionSesion( Empleado pEmpleado ) throws SQLException {
        mainWindowModel = new MainWindowModel( pEmpleado );
        label_NombreUsuario.setText(mainWindowModel.getEmpleado().getNombre() + mainWindowModel.getEmpleado().getApellido1());
        label_IdEmpleado.setText( "" + mainWindowModel.getEmpleado().getId());
        label_PuestoEmpleado.setText("Id: " + mainWindowModel.getEmpleado().getPuesto());

        setBotonesTablas(this.mainWindowModel.getListaTablas()); //Carga los botones de la izquierda para acceder a las tablas
        cargarTabla();                                           // Carga una tabla en el centro de la pantalla, mientras es temporal

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
            case "btn_menu_nuevo":
                System.out.println("Insert en la tabla");
                insertarNuevo();
                break;
            case "btn_menu_actualizar":
                System.out.println("Update de la fila seleccionada en la tabla");
                break;
            case "btn_menu_borrar":
                System.out.println("Borrar la fila seleccionada en la tabla");
                break;
            case"btn_CerrarSesion":
                System.out.println("Cerrando Sesion");
                break;
        }
    }

    //Ejemplo de como cargar la tabla
    private void cargarTabla(){
        //Crear una nueva Table View, del tipo de datos que se le va a ingresar,
        //Crear las columnas, con strings
        //Agregar el table view al anchor pane


        Empleado empleado1 = new Empleado("5","6","Pastelero","Prueba1", "Prueba1","Rod","89898989");
        Empleado empleado2 = new Empleado("6","7","Pastelero","Prueba1", "Prueba1","Rod","89898989");
        Empleado empleado3 = new Empleado("7","7","Pastelero","Prueba1", "Prueba1","Rod","89898989");

        ObservableList< Empleado > empleados = FXCollections.observableArrayList(); //Esta lista se debe obtener desde el OJBDC
        empleados.add(empleado1);
        empleados.add(empleado2);
        empleados.add(empleado3);

        TableColumn<Empleado, String> idColumn = new TableColumn<>("id_empleado");
        idColumn.setCellValueFactory( new PropertyValueFactory<>("id"));

        TableColumn<Empleado, String> idSucursalColumn = new TableColumn<>("id_sucursal");
        idSucursalColumn.setCellValueFactory( new PropertyValueFactory<>("id_sucursal"));

        TableColumn<Empleado, String> puestoColum = new TableColumn<>("puesto");
        puestoColum.setCellValueFactory( new PropertyValueFactory<>("puesto"));

        TableColumn<Empleado, String> nombreColumn = new TableColumn<>("nombre");
        nombreColumn.setCellValueFactory( new PropertyValueFactory<>("nombre"));

        TableColumn<Empleado, String> ap1Column = new TableColumn<>("apellido1");
        ap1Column.setCellValueFactory( new PropertyValueFactory<>("apellido1"));

        TableColumn<Empleado, String> ap2Column = new TableColumn<>("apellido2");
        ap2Column.setCellValueFactory( new PropertyValueFactory<>("apellido2"));

        TableColumn<Empleado, String> telColumn = new TableColumn<>("telefono");
        telColumn.setCellValueFactory( new PropertyValueFactory<>("telefono"));

        TableView<Empleado> tablaEmpleado = new TableView<>(); //Se crea un table view
        tablaEmpleado.setItems(empleados);
        tablaEmpleado.getColumns().setAll(idColumn, idSucursalColumn, puestoColum, nombreColumn, ap1Column,ap2Column,telColumn);


        borderPane_centerTable.setCenter(tablaEmpleado); //Se coloca el Table View en el centro del Border Pane
    }

    private void insertarNuevo (){
        //Saber primero que tabla es la que tenemos en la vista
        //Para sacar la lista de columnas
        System.out.println("Insertar Nuevo");

       // ObservableList <TableColumn<Empleado,String>> columnasTablaActual = tableView_tablaActual.getColumns();
    }

    //Cuando se Presiona el boton de buscar acá se hace la lógica de la búsqueda
    private void busquedaTabla(String pBusqueda){
        String busqueda = tf_menu_buscar.getText();
        System.out.println("Buscando " + busqueda + " por: " + pBusqueda);
    }

    //Cambia la busqueda con el menu button
    public void cambiarBusqueda(Event e){
        String textMenu = ((MenuItem) e.getSource() ).getText();
        btn_menu_buscar.setText(textMenu);
    }


}
