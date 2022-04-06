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
import javafx.scene.control.cell.TextFieldTableCell;
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
        cargarTabla("EMPLEADO");                                           // Carga una tabla en el centro de la pantalla, mientras es temporal

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
    //Deben estar los botones de cada tabla
    //Que llaman a cargar la tabla de la GUI con el nombre de esa tabla.
    public void handlerBotonesMain(Event e ){

        String id = ((Node) e.getSource()).getId();

        System.out.println("Boton presionado: " + id);

        switch(id) {
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
                eliminarElemento();
                break;
            case"btn_CerrarSesion":
                System.out.println("Cerrando Sesion");
                break;
            default:

                cargarTabla(id);  //Le da el Id para saber que tabla cargar
                break;
        }
    }


    //Ejemplo de como cargar la tabla
    private void cargarTabla(String id){
        //Crear una nueva Table View, del tipo de datos que se le va a ingresar,
        //Crear las columnas, con strings
        //Agregar el table view al anchor pane

        //Elimina la Tabla Actual antes de cargar la siguiente:
        try{
            borderPane_centerTable.getChildren().remove(1) ; //Index 0 es la barra de herramientas de arriba
        }catch (Exception e){
            System.out.println("Error al Eliminar tabla, No existe la tabla");
        }


        //Aca sabemos el nombre de la tabla a cargar

        String tablaCargar = id.replace("btn_","");

        switch (tablaCargar){
            case "EMPLEADO":
                //Acá debemos pedir al MainWindowModel la lista de ObservableList de la base de datos

                Empleado empleado1 = new Empleado("5","6","Pastelero","Prueba1", "Prueba1","Rod","89898989");
                Empleado empleado2 = new Empleado("6","7","Pastelero","Prueba1", "Prueba1","Rod","89898989");
                Empleado empleado3 = new Empleado("7","7","Pastelero","Prueba1", "Prueba1","Rod","89898989");

                ObservableList< Empleado > empleados = FXCollections.observableArrayList(); //Esta lista se debe obtener desde el OJBDC
                empleados.add(empleado1);
                empleados.add(empleado2);
                empleados.add(empleado3);

                //Le pasamos los datos y el nombre de la tabla a la funcion de cargar la tabla de empleado
                cargarTablaEmpleado(empleados, tablaCargar);
                break;
            case "SUCURSAL":
                //cargarTablaSucursal();
                break;
            case "PRODUCTO":
                //cargarTablaProducto();
                break;
            case "CLIENTE":
                //cargarTablaCliente();
                break;
            case "PEDIDO":
                //cargarTablaPedido();
                break;
            case "BITACORA":
                //cargarTablaBitacora();
                break;
            case "USUARIO":
                //cargarTablaUsuario();
                break;
            case "INGREDIENTE":
                //cargarTablaIngrediente();
                break;
            case "PROVEEDOR":
                //cargarTablaProveedor();
                break;
            case "PRECIOS":
                //cargarTablaPrecios();
                break;
            case "LISTAINGREDIENTES":
                //cargarTablaListaIngredientes();
                break;
            case "INVENTARIOS":
                //cargarTablaInventarios();
                break;
        }

    }

    //Carga la Tabla de Empleados en la Vista
    private void cargarTablaEmpleado( ObservableList<Empleado> listaEmpleados, String idTabla ){
        //
        System.out.println("Cargando Tabla EMPLEADO");

        //Le dice al Modelo la tabla que está a punto de cargar
        mainWindowModel.setTablaActual(idTabla);

        //Crea las columnas de la tabla
        TableColumn<Empleado, String> idColumn = new TableColumn<>("id_empleado");
        idColumn.setCellValueFactory( new PropertyValueFactory<>("id"));

        TableColumn<Empleado, String> idSucursalColumn = new TableColumn<>("id_sucursal");
        idSucursalColumn.setCellValueFactory( new PropertyValueFactory<>("id_sucursal"));
        idSucursalColumn.setEditable(true);                                                        //Para poder editar
        idSucursalColumn.setCellFactory(TextFieldTableCell.forTableColumn());                      //Para que cuando se presione se coloque un textfiel
        idSucursalColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() { //Cuando se edita el textfield entonces se cambia el elemento en la lista
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setId_sucursal(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableColumn<Empleado, String> puestoColum = new TableColumn<>("puesto");
        puestoColum.setCellValueFactory( new PropertyValueFactory<>("puesto"));
        puestoColum.setEditable(true);
        puestoColum.setCellFactory(TextFieldTableCell.forTableColumn());
        puestoColum.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setPuesto(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableColumn<Empleado, String> nombreColumn = new TableColumn<>("nombre");
        nombreColumn.setCellValueFactory( new PropertyValueFactory<>("nombre"));
        nombreColumn.setEditable(true);
        nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setNombre(empleadoStringCellEditEvent.getNewValue());
            }
        });


        TableColumn<Empleado, String> ap1Column = new TableColumn<>("apellido1");
        ap1Column.setCellValueFactory( new PropertyValueFactory<>("apellido1"));
        ap1Column.setEditable(true);
        ap1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        ap1Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setApellido1(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableColumn<Empleado, String> ap2Column = new TableColumn<>("apellido2");
        ap2Column.setCellValueFactory( new PropertyValueFactory<>("apellido2"));
        ap2Column.setEditable(true);
        ap2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        ap2Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setApellido2(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableColumn<Empleado, String> telColumn = new TableColumn<>("telefono");
        telColumn.setCellValueFactory( new PropertyValueFactory<>("telefono"));
        telColumn.setEditable(true);
        telColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        telColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setTelefono(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableView<Empleado> tablaEmpleado = new TableView<>(); //Se crea un table view
        tablaEmpleado.setEditable(true);
        tablaEmpleado.setItems(listaEmpleados);                     //Se setean los datos al table view
        tablaEmpleado.setId("tablaActual");      //Tiene que ser siempre el mismo
        tablaEmpleado.getColumns().setAll(idColumn, idSucursalColumn, puestoColum, nombreColumn, ap1Column,ap2Column,telColumn); //Se agregan las columnas a la vista

        borderPane_centerTable.setCenter(tablaEmpleado); //Se coloca el Table View en el centro del Border Pane
    }

    //Consigue la tabla de MainTableWindowModel
    //Elimina el elemento
    private void eliminarElemento () {

        System.out.println("Eliminando Elemento");

        //Acá habría que poner un case para cada tabla
        switch (mainWindowModel.getTablaActual()){
            case "EMPLEADO":
                //Acá hay que llamar al tablewindowmodel que le diga al JBDC que elimine el elemento
                // Que luego nos devuelva la tabla que acaba de modificar
                //cargar la tabla de nuevo
                Empleado empleadoAEliminar = ((TableView<Empleado>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                //Se manda a eliminar el elemento del empleado con el id
                System.out.println("Eliminando Empleado con id "+ empleadoAEliminar.getId() + " y nombre " + empleadoAEliminar.getNombre());
                break;
            case "SUCURSAL":
                //cargarTablaSucursal();
                break;
            case "PRODUCTO":
                //cargarTablaProducto();
                break;
            case "CLIENTE":
                //cargarTablaCliente();
                break;
            case "PEDIDO":
                //cargarTablaPedido();
                break;
            case "BITACORA":
                //cargarTablaBitacora();
                break;
            case "USUARIO":
                //cargarTablaUsuario();
                break;
            case "INGREDIENTE":
                //cargarTablaIngrediente();
                break;
            case "PROVEEDOR":
                //cargarTablaProveedor();
                break;
            case "PRECIOS":
                //cargarTablaPrecios();
                break;
            case "LISTAINGREDIENTES":
                //cargarTablaListaIngredientes();
                break;
            case "INVENTARIOS":
                //cargarTablaInventarios();
                break;
        }

    }

    private void actualizarElemento(){

        switch (mainWindowModel.getTablaActual()){
            case "EMPLEADO":
                //Hay que crear un alert view con textfields para cada columna de la tabla actual
                //Tomar los valores de ese alert view y mandarlo a actualizar a la base de datos.
                Empleado empleadoAActualizar = ((TableView<Empleado>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                System.out.println("Nuevos Valores del empleado " + empleadoAActualizar);
                break;
            case "SUCURSAL":
                //cargarTablaSucursal();
                break;
            case "PRODUCTO":
                //cargarTablaProducto();
                break;
            case "CLIENTE":
                //cargarTablaCliente();
                break;
            case "PEDIDO":
                //cargarTablaPedido();
                break;
            case "BITACORA":
                //cargarTablaBitacora();
                break;
            case "USUARIO":
                //cargarTablaUsuario();
                break;
            case "INGREDIENTE":
                //cargarTablaIngrediente();
                break;
            case "PROVEEDOR":
                //cargarTablaProveedor();
                break;
            case "PRECIOS":
                //cargarTablaPrecios();
                break;
            case "LISTAINGREDIENTES":
                //cargarTablaListaIngredientes();
                break;
            case "INVENTARIOS":
                //cargarTablaInventarios();
                break;
        }
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
